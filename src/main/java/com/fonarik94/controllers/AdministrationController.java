package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.exceptions.ResourceNotFoundException;
import com.fonarik94.repositories.PostRepository;
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

    @Autowired
    public AdministrationController(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        Post oldPost = postRepository
                .findById(post.getId())
                .orElseThrow(ResourceNotFoundException::new);
        oldPost.setHeader(post.getHeader());
        oldPost.setText(post.getText());
        oldPost.setPublished(post.isPublished());
        postRepository.save(oldPost);
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping(value = "/postwriter/addpost")
    public ModelAndView addPost(@ModelAttribute("post") Post post) {
        if (post.isPublished()){
            post.setPublicationDate(LocalDateTime.now());
        }
        postRepository.save(post);
        log.debug(">> Post added");
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @GetMapping(value = "/wol")
    public String wol(){
        return "wol";
    }

    @PostMapping(value = "/wol")
    public ModelAndView wolSender(@RequestParam("mac") String mac) {
        WakeOnLan.wake(mac);
        log.debug("Wake-on-lan request. MAC: " + mac);
        return new ModelAndView("redirect:/administration/wol");
    }

    @DeleteMapping(value = "/postwriter/delete/post/{postId:[\\d]+}/comment/{commentId:[\\d]+}")
    @ResponseBody
    public String deleteComment(@PathVariable int commentId, @PathVariable int postId) {
        Post post =  postRepository
                .findById(postId)
                .orElseThrow(ResourceNotFoundException::new);
        post.getComments().removeIf(comment -> comment.getId() == commentId);
        postRepository.save(post);
        return "deleted";
    }

    @DeleteMapping(value = "postwriter/delete/post/{postId:[\\d]+}")
    @ResponseBody
    public String deletePost(@PathVariable int postId){
        if (!postRepository.existsById(postId)){
            throw new ResourceNotFoundException();
        }else {
            postRepository.deleteById(postId);
        }
        return "deleted";
    }


}