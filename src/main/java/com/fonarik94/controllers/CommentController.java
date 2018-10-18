package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class CommentController {
    private final PostDao postDao;

    @PostMapping(value = "/post/{id:[\\d]+}")
    public String addComment(@Valid @ModelAttribute Comment comment,
                             BindingResult bindingResult,
                             @PathVariable("id") int postId,
                             RedirectAttributes redirectAttr){
        if(!bindingResult.hasErrors()){
            postDao.addComment(postId, comment);
        } else {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult." + bindingResult.getObjectName(), bindingResult);
            redirectAttr.addFlashAttribute("comment", comment);
        }
        return "redirect:/post/{id}";
    }

    @Autowired
    public CommentController(PostDao postDao) {
        this.postDao = postDao;
    }

}
