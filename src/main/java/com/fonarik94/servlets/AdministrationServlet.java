package com.fonarik94.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ярослав on 08.04.2017.
 */
public class AdministrationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst("/administration/", "");
        switch (path){
            case "wol": request.setAttribute("requestedPage", "wol.jsp");
            case "postWrighter": request.setAttribute("requestedPge", "redactor.jsp");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/administration.jsp");
        dispatcher.forward(request,response);
    }
}
