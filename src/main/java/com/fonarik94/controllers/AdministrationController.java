package com.fonarik94.controllers;

import com.fonarik94.repo.CommentRepository;
import com.fonarik94.repo.PostDao;
import com.fonarik94.domain.Post;
import com.fonarik94.repo.PostRepository;
import com.fonarik94.utils.WakeOnLan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping(value = "/administration")
public class AdministrationController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AdministrationController(PostDao postDao, PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping()
    public String wol(Model model) {
        return "wol";
    }

    @GetMapping(value = "/postwriter")
    public String postWriter(Model model) {
        model.addAttribute("allPosts", postRepository.findAll());
        return "redactor";
    }

    @GetMapping(value = "/postwriter/addpost")
    public String addPostButton(Model model) {
        model.addAttribute("post", new Post());
        return "addEditPost";
    }

    @GetMapping(value = "/postwriter/edit")
    public String editPostButton(@RequestParam("editbyid") int id, Model model) {
        model.addAttribute("post", postRepository.findById(id).get());
        log.debug(">> id " + postRepository.findById(id).get().getId());
        return "addEditPost";
    }

    @PostMapping(value = "/postwriter/edit")
    public ModelAndView editById(@ModelAttribute("post") Post post) {
        Post oldPost = postRepository.findById(post.getId()).get();
        oldPost.setHeader(post.getHeader());
        oldPost.setText(post.getText());
        oldPost.setPublished(post.isPublished());
        postRepository.save(oldPost);
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping(value = "/postwriter/addpost")
    public ModelAndView addPost( @ModelAttribute("post") Post post) {
        if (post.isPublished()){
            post.setPublicationDateTime(LocalDateTime.now());
        }
        postRepository.save(post);
        log.debug(">> Post added");
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping()
    public ModelAndView wolSender(@RequestParam("mac") String mac) {
        WakeOnLan.wake(mac);
        log.debug("Wake-on-lan request. MAC: " + mac);
        return new ModelAndView("redirect:/administration");
    }

    @DeleteMapping(value = "/postwriter/delete/comment/{commentId:[\\d]+}")
    @ResponseBody
    public String deleteComment(@PathVariable int commentId) {
            commentRepository.deleteById(commentId);
        return "deleted";
    }

    @DeleteMapping(value = "postwriter/delete/post/{postId:[\\d]+}")
    @ResponseBody
    public String deletePost(@PathVariable int postId){
        postRepository.deleteById(postId);
        return "deleted";
    }


}