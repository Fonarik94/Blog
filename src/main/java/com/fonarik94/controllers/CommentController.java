package com.fonarik94.controllers;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;
import com.fonarik94.repo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class CommentController {
    @Autowired
    PostRepository postRepository;
    @PostMapping(value = "/post/{id:[\\d]+}")
    public String addComment(@Valid @ModelAttribute Comment comment,
                             BindingResult bindingResult,
                             @PathVariable int id,
                             RedirectAttributes redirectAttr){

        if(!bindingResult.hasErrors()){
            Post post = postRepository.findById(id).get();
            post.addComment(comment);
            postRepository.save(post);

        } else {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + bindingResult.getObjectName(), bindingResult);
            redirectAttr.addFlashAttribute("comment", comment);
        }
        return "redirect:/post/{id}";
    }

}
