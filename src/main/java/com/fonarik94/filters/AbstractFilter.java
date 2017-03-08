package com.fonarik94.filters;

import javax.servlet.*;
import java.io.IOException;

public abstract class AbstractFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    public void destroy() {

    }
}
