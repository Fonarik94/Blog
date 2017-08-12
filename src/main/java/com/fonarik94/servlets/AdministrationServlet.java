package com.fonarik94.servlets;

import com.fonarik94.dao.Post;
import com.fonarik94.dao.PostDao;
import com.fonarik94.dao.PostDaoImpl;
import com.fonarik94.utils.WakeOnLan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;
import static com.fonarik94.utils.WakeOnLan.wake;

public class AdministrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst("/administration/", "");
        switch (path) {
            case "wol":
                request.setAttribute("requestedPage", "/jsp/wol.jsp");
                break;
            case "postWriter":
                request.setAttribute("requestedPage", "/jsp/redactor.jsp");
                request.setAttribute("redactor", 1);
                break;
            case "postWriter/addPost":
                request.setAttribute("requestedPage", "/jsp/addEditPost.jsp");
                break;
            case "postWriter/edit":
                logger.debug(">> Request to edit post with id: " + request.getParameter("editById"));
                request.setAttribute("editMode", PostDaoImpl.getInstance().getPostById(Integer.parseInt(request.getParameter("editById"))));
                request.setAttribute("requestedPage", "/jsp/addEditPost.jsp");
                break;
            default:
                request.setAttribute("requestedPage", "/jsp/wol.jsp"); //displayed page on path "/administration"
        }
        logger.debug(">> requestedPage: " + request.getAttribute("requestedPage"));
        logger.debug(">> edit mode: " + request.getAttribute("editMode"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/administration.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hiddenParameter = request.getParameter("Page");
        logger.debug(">> Post hidden parameter value is: " + hiddenParameter);
        switch (hiddenParameter) {
            case "Delete":
                deletePost(Integer.parseInt(request.getParameter("DeleteById")));
                response.sendRedirect("/administration/postWriter");
                break;
            case "Add":
                addPost(request);
                response.sendRedirect("/administration/postWriter");
                break;
            case "Edit":
                logger.debug(">> edit by id: " + request.getParameter("editById"));
                editPost(request);
                response.sendRedirect("/administration/postWriter");
                break;
            case "WOL":
                WakeOnLan.wake(request.getParameter("MAC"));
                response.sendRedirect("/administration/wol");
                break;
        }
    }

    private void addPost(HttpServletRequest request) {
        PostDao postDao = PostDaoImpl.getInstance();
        String checkBoxParameter = request.getParameter("isPublished");
        logger.debug("Checkbox value is : " + checkBoxParameter);
        boolean isPublishedCheckBox = false;
        if (checkBoxParameter != null) {
            isPublishedCheckBox = checkBoxParameter.equalsIgnoreCase("on");
        }
        postDao.addPost(request.getParameter("postHeaderInput"),
                request.getParameter("postTextInput"),
                isPublishedCheckBox);
    }

    private void editPost(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("editById"));
            Post updatedPost = new Post.PostBuilder()
                    .setPostHeader(request.getParameter("postHeaderInput"))
                    .setPostText(request.getParameter("postTextInput"))
                    .setPublished(request.getParameter("isPublished") != null)
                    .setCreationDateTime(null)
                    .setPublicationDateTime(null)
                    .setPostId(id)
                    .build();

            PostDaoImpl.getInstance().editPostById(id, updatedPost);
        } catch (NumberFormatException nfe) {
            logger.error(">> Wrong post ID while edit post" + nfe.toString());
        }
    }

    private void deletePost(int id) {
        PostDao postDao = PostDaoImpl.getInstance();
        postDao.deletePostByID(id);
    }
}
