package com.fonarik94.servlets;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import static com.fonarik94.utils.ClassNameUtil.*;
import java.util.Arrays;

public class FileRequestServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder pathToRequestedFile = new StringBuilder();
        pathToRequestedFile.append(getServletContext().getRealPath(File.separator)).append(File.separator)
                .append("WEB-INF").append(File.separator).append("classes").append(File.separator);
        String [] requestedFile = request.getRequestURI().split("/");
        for (int i=2; i<requestedFile.length; i++) {
            pathToRequestedFile.append(requestedFile[i]).append(File.separator);
        }
        pathToRequestedFile.deleteCharAt(pathToRequestedFile.length()-1);
        String normalPath = pathToRequestedFile.toString().replaceAll("\\\\", "\\\\\\\\");
        response.setContentType(getServletContext().getMimeType(requestedFile[requestedFile.length-1]));

        logger.debug(requestedFile[requestedFile.length-1]+"MIME type is "+getServletContext().getMimeType(requestedFile[requestedFile.length-1]));


        BufferedInputStream bufferedFileInputStream = new BufferedInputStream(new FileInputStream(normalPath));
        BufferedOutputStream responseOutputStream = new BufferedOutputStream(response.getOutputStream());

        int val;
        while ((val=bufferedFileInputStream.read())!=-1){
            responseOutputStream.write(val);
        }
        bufferedFileInputStream.close();
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
