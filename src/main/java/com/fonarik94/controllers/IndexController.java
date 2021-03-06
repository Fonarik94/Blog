package com.fonarik94.controllers;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;
import com.fonarik94.exceptions.ResourceNotFoundException;
import com.fonarik94.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(value = "/")
public class IndexController {
    private final PostRepository postRepository;
    @GetMapping()
    public String main(Model model){
        model.addAttribute("publishedPosts", postRepository.findPublished());
        return "posts";
    }

    @GetMapping(value = "about")
    public String about(Model model){
        model.addAttribute("aboutPage", postRepository.findById(1).orElse(new Post("about page", "Add new about page"))); //Post ID 1 is about page
        return "about";
    }

    @GetMapping(value = "post/{id:[\\d]+}")
    public String getPost(@PathVariable ("id") int id, Model model){
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("post", post);
        if(!model.containsAttribute("comment")) {
            model.addAttribute("comment", new Comment()); // for validation
        }
        return "singlePost";
    }

    @Autowired
    public IndexController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
