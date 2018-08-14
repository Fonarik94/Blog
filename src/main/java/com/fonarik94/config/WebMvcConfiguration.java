package com.fonarik94.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.GzipResourceResolver;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**", "/favicon.ico")
                .addResourceLocations("/resources/", "classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(false)
                .addResolver(new GzipResourceResolver());
    }

}
