package com.fonarik94.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.fonarik94.utils.ClassNameUtil.getCurrentClassName;

public class FileRequestServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurrentClassName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String fileURI = "WEB-INF/classes" + request.getRequestURI().replaceFirst("/resources", "");
        fileURI = fileURI.replace("/", File.separator);

        response.setContentType(getServletContext().getMimeType(fileURI));
        try (InputStream requestedFileInputStream = getServletContext().getResourceAsStream(fileURI);
             InputStream bufferedFileInputStream = new BufferedInputStream(requestedFileInputStream);
             OutputStream responseOutputStream = new BufferedOutputStream(response.getOutputStream())) {
            int val;
            while ((val = bufferedFileInputStream.read()) != -1) {
                responseOutputStream.write(val);
            }
            responseOutputStream.flush();
        } catch (IOException e) {
            logger.error(">> " + e.toString());
        }
    }
}
