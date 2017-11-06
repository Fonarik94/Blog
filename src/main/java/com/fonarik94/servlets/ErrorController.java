package com.fonarik94.servlets;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ErrorController implements ErrorViewResolver{

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest httpServletRequest, HttpStatus httpStatus, Map<String, Object> map) {
        return null;
    }
}
