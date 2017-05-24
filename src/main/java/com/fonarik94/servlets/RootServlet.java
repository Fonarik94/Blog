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

/**
 * Created by Ярослав on 15.02.2017.
 */
public class RootServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        logger.debug(">> Root servlet");*/
        String path = request.getRequestURI();
        logger.debug(">> requested path is: " + path);
        switch (path){
            case "/":request.setAttribute("requestedPage", "/jsp/posts.jsp");
            break;
            case "/about": request.setAttribute("requestedPage", "/jsp/about.jsp");
            break;
            default:request.setAttribute("requestedPage", "/jsp/posts.jsp");
        }
        logger.debug("requestedPage: "+ request.getAttribute("requestedPage"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/template.jsp");
        dispatcher.forward(request,response);
    }
}
