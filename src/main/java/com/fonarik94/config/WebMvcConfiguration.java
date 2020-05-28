package com.fonarik94.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

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
