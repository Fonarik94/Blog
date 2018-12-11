package com.fonarik94.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Value("${images.path}")
    private String imagesPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**", "/favicon.ico")
                .addResourceLocations("/resources/", "classpath:/static/", "file:///"+imagesPath+"/" )
                .setCachePeriod(3600)
                .resourceChain(false);
/*        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:///"+imagesPath+"/");*/
    }

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/administration/wol").setViewName("wol");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/post").setViewName("redirect:/");
        registry.addViewController("/administration").setViewName("redirect:/");
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
