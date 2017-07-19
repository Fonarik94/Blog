package com.fonarik94.servlets;

import com.fonarik94.dao.DBWorker;
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
import java.sql.SQLException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

/**
 * Created by Ярослав on 15.02.2017.
 */
public class RootServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        switch (path){
            case "/":request.setAttribute("requestedPage", "/jsp/posts.jsp");
            break;
            case "/about": request.setAttribute("requestedPage", "/jsp/about.jsp");
            break;
            case "/read": read(request, response);
            break;
            default:request.setAttribute("requestedPage", "/jsp/posts.jsp");
        }
        logger.info(">> Get request from IP: "+ request.getRemoteHost());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/template.jsp");
        dispatcher.forward(request,response);
    }

    private void read(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int postId = Integer.valueOf(request.getParameter("postId"));
        logger.debug(">> Requested post with ID = " + postId);
        PostDao postDao = PostDaoImpl.getInstance();
        Post requestedPost = postDao.getPostById(postId);
        request.setAttribute("requestedPost", requestedPost);
        request.setAttribute("requestedPage", "singlePost.jsp");
//        response.sendRedirect("postId=" + postId);

    }
}
