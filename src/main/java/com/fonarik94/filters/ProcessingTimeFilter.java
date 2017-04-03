package com.fonarik94.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class ProcessingTimeFilter extends AbstractFilter {
    private static Logger log = LogManager.getLogger(getCurentClassName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        log.debug(">> Request processed in "+(System.currentTimeMillis()-startTime)+" ms");
    }
}
