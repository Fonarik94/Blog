package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class IndexController {
    @Autowired PostDao postDao;
    @Autowired HttpServletRequest request;
    @GetMapping(value = "/")
    public String main(Model model){
        model.addAttribute("requestedPage", "posts.ftl");
        model.addAttribute("publishedPosts", postDao.getPublishedPosts());

        log.debug(">> Client IP: " + getClientIp(request));
    return "template";
    }

    @GetMapping(value = "/about")
    public String about(Model model){
        model.addAttribute("aboutPage", postDao.getPostById(1)); //Post ID 1 is about page
        model.addAttribute("requestedPage", "about.ftl");
        return "template";
    }

    @GetMapping(value = "/post/{id:[\\d]+}")
    public String getPost(@PathVariable ("id") int id, Model model){
        model.addAttribute("requestedPost", postDao.getPostById(id));
        model.addAttribute("requestedPage", "singlePost.ftl");
        return "template";
    }

    private String getClientIp(HttpServletRequest request){
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if(clientIp == null){
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
