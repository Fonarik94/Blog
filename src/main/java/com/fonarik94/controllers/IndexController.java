package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.repo.PostDao;
import com.fonarik94.domain.Comment;
import com.fonarik94.repo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping(value = "/")
public class IndexController {
    private final HttpServletRequest request;
    private final PostRepository postRepository;

    @GetMapping()
    public String main(Model model){
        model.addAttribute("publishedPosts", postRepository.findByPublishedTrueOrderByPublicationDateTimeDesc());
        log.debug(">> Client IP: " + getClientIp(request));
        return "posts";
    }

    @GetMapping(value = "about")
    public String about(Model model){
        model.addAttribute("aboutPage", postRepository.findById(1)); //Post ID 1 is about page
        return "about";
    }

    @GetMapping(value = "post/{id:[\\d]+}")
    public String getPost(@PathVariable ("id") int id, Model model){
        Post post = postRepository.findById(id).get();
        model.addAttribute("post", post);
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
    public IndexController(HttpServletRequest request, PostRepository postRepository) {
        this.request = request;
        this.postRepository = postRepository;
    }
}
