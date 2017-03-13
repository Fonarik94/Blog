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
import static com.fonarik94.utils.WakeOnLan.wake;

public class WOL extends HttpServlet {
    private static final Logger log = LogManager.getLogger(getCurentClassName());


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mac = request.getParameter("MAC");
        if (mac != null) {
            wake(mac);
            log.info(">> WOL package sent");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/wol.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mac = request.getParameter("MAC");
        if (mac != null) {
            wake(mac);
            log.info(">> WOL package sent");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/wol.jsp");
        dispatcher.forward(request, response);
    }
}

