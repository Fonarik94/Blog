package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Post;
import com.fonarik94.utils.WakeOnLan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class AdministrationController {
    private final PostDao postDao;

    @Autowired
    public AdministrationController(PostDao postDao) {
        this.postDao = postDao;
    }

    @GetMapping(value = "/administration")
    public String wol(Model model) {
        model.addAttribute("requestedPage", "/wol.ftl");
        return "administration";
    }

    @GetMapping(value = "/administration/postwriter")
    public String postWriter(Model model) {
        model.addAttribute("allPosts", postDao.getAllPosts());
        model.addAttribute("requestedPage", "/redactor.ftl");
        return "administration";
    }

    @GetMapping(value = "/administration/postwriter/addpost")
    public String addPostButton(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @GetMapping(value = "/administration/postwriter/edit")
    public String editPostButton(@RequestParam("editbyid") int id, Model model) {
        model.addAttribute("post", postDao.getPostById(id));
        model.addAttribute("comments", postDao.getComments(id));
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @PostMapping(value = "/administration/postwriter/edit")
    public ModelAndView editById(@ModelAttribute("post") Post post) {
        postDao.editPostById(post.getId(), post.getHeader(), post.getText(), post.isPublished());
        log.debug(">> Edited post with id: " + post.getId());
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping(value = "/administration/postwriter/addpost")
    public ModelAndView addPost( @ModelAttribute("post") Post post) {
        postDao.addPost(post.getHeader(), post.getText(), post.isPublished());
        log.debug(">> Post added");
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping(value = "/administration")
    public ModelAndView wolSender(@RequestParam("mac") String mac) {
        WakeOnLan.wake(mac);
        log.debug("Wake-on-lan request. MAC: " + mac);
        return new ModelAndView("redirect:/administration");
    }

    @DeleteMapping(value = "/administration/postwriter/delete/{type:[\\w]+}/{id:[\\d]+}")
    @ResponseBody
    public String ajaxDelete(@PathVariable String type, @PathVariable int id) {
        log.debug(">> Delete "+type+" with ID = " + id);
        if(type.equalsIgnoreCase("post")){
            postDao.deletePostById(id);
        }else if (type.equalsIgnoreCase("comment")){
            postDao.deleteCommentById(id);
        }
        return "deleted";
    }

}