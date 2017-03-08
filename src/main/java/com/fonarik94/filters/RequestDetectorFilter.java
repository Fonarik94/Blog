package com.fonarik94.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

/**
 * Created by Ярослав on 01.02.2017.
 */
public class RequestDetectorFilter extends AbstractFilter {
    private static Logger log = LogManager.getLogger(getCurentClassName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Request from IP: " + request.getRemoteAddr());
        chain.doFilter(request,response);
    }
}
