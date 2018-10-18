package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class IndexController {
    private final PostDao postDao;
    private final HttpServletRequest request;

    @GetMapping(value = "/")
    public String main(Model model){
        model.addAttribute("publishedPosts", postDao.getPublishedPosts());
        log.debug(">> Client IP: " + getClientIp(request));
        return "posts";
    }

    @GetMapping(value = "/about")
    public String about(Model model){
        model.addAttribute("aboutPage", postDao.getPostById(1)); //Post ID 1 is about page
        return "about";
    }


    @GetMapping(value = "/post/{id:[\\d]+}")
    public String getPost(@PathVariable ("id") int id, Model model){
        model.addAttribute("comments", postDao.getComments(id));
        model.addAttribute("requestedPost", postDao.getPostById(id));
        if(!model.containsAttribute("comment")) {
            model.addAttribute("comment", new Comment()); // for validation
        }
        return "singlePost";
    }

    private String getClientIp(HttpServletRequest request){
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if(clientIp == null){
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    @Autowired
    public IndexController(PostDao postDao, HttpServletRequest request) {
        this.postDao = postDao;
        this.request = request;
    }
}
