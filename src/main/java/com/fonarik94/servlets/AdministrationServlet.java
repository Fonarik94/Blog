package com.fonarik94.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class AdministrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst("/administration/", "");
        switch (path){
            case "wol": request.setAttribute("requestedPage", "/jsp/wol.jsp");
            break;
            case "postWriter": request.setAttribute("requestedPage", "/jsp/redactor.jsp");
            break;
            case "postWriter/addPost": request.setAttribute("requestedPage", "/jsp/addPost.jsp");
            break;
            default: request.setAttribute("requestedPage", "/jsp/wol.jsp"); //displayed page on path "/administration"
        }
        logger.debug("requestedPage: "+ request.getAttribute("requestedPage"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/administration.jsp");
        dispatcher.forward(request,response);
    }
}
