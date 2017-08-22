package com.fonarik94.servlets;

import com.fonarik94.dao.Post;
import com.fonarik94.dao.PostDao;
import com.fonarik94.dao.PostDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

/**
 * Created by Ярослав on 15.02.2017.
 */
public class RootServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    private PostDao postDao = new PostDaoImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(">> Get request from IP: " + getClientIp(request));
        boolean error = false;
        String path = request.getRequestURI();
        switch (path) {
            case "/":
            case "/blog":
                request.setAttribute("requestedPage", "/jsp/posts.jsp");
                request.setAttribute("publishedPosts", postDao.getListOfAllPosts(true));
                break;
            case "/about":
                getAboutPage(request);
                break;
            case "/read":
                read(request);
                break;
            default:
                error = true;
        }
        if (error) {
            response.sendError(404, "You are broke the internet!");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/template.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getAboutPage(HttpServletRequest request) {
        Post aboutPage = postDao.getPostById(1); //ID 1 is about page
        request.setAttribute("aboutPage", aboutPage);
        request.setAttribute("requestedPage", "/jsp/about.jsp");
    }

    private void read(HttpServletRequest request) throws IOException {
        int postId = Integer.valueOf(request.getParameter("postId"));
        logger.debug(">> Requested post with ID = " + postId);
        Post requestedPost = postDao.getPostById(postId);
        request.setAttribute("requestedPost", requestedPost);
        request.setAttribute("requestedPage", "singlePost.jsp");
    }

    /**
     * Method returns client ip in cases of use nginx
     *
     * @param request HTTP request
     * @return String clientIP
     */
    private static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = clientIp.split(",")[0].trim();
        }
        return clientIp;
    }


}
