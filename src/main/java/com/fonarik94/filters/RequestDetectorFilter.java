package com.fonarik94.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class RequestDetectorFilter extends AbstractFilter {
    private static Logger log = LogManager.getLogger(getCurentClassName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestedPath = ((HttpServletRequest) request).getRequestURI();

        chain.doFilter(request,response);
    }
}
