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
public class AdministrationServlet {
    @Autowired PostDao postDao;

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
        model.addAttribute("postHeader", "");
        model.addAttribute("text", "");
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @GetMapping(value = "/administration/postwriter/edit")
    public String editPostButton(@RequestParam("editbyid") int id, Model model) {
        Post post = postDao.getPostById(id);
        model.addAttribute("postHeader", post.getHeader());
        model.addAttribute("text", post.getText());
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @PostMapping(value = "/administration/postwriter/edit")
    public ModelAndView editById(@RequestParam("editbyid") int id,
                                 @RequestParam("postHeaderInput") String header,
                                 @RequestParam("postTextInput") String text,
                                 @RequestParam(value = "isPublished", required = false) String isPublised) {

        postDao.editPostById(id, header, text, isPublised != null);
        log.debug(">> Edited post with id: " + id);
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @PostMapping(value = "/administration/postwriter/addpost")
    public ModelAndView addPost(@RequestParam("postHeaderInput") String postHeader,
                                @RequestParam("postTextInput") String text,
                                @RequestParam(value = "isPublished", required = false) String isPublished) {
        boolean published;
        published = isPublished != null;
        postDao.addPost(postHeader, text, published);
        log.debug(">> Post added");
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @RequestMapping(value = "/administration", method = RequestMethod.POST)
    public ModelAndView wolSender(@RequestParam("mac") String mac) {
        WakeOnLan.wake(mac);
        log.debug("Wake-on-lan request. MAC: " + mac);
        return new ModelAndView("redirect:/administration");
    }

    @RequestMapping(value = "/administration/postwriter/delete", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxDelete(@RequestParam("deleteById") int id) {
        log.debug(">> Deleted post with ID = " + id);
        postDao.deletePostById(id);
        return "deleted";
    }



}