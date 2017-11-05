package com.fonarik94.servlets;

import com.fonarik94.domain.Post;
import com.fonarik94.dao.PostDao;
import com.fonarik94.utils.WakeOnLan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrationServlet {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    PostDao postDao;

    @RequestMapping(value = "/administration")
    public String wol(Model model) {
        model.addAttribute("requestedPage", "/wol.ftl");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter")
    public String postWriter(Model model) {
        model.addAttribute("allPosts", postDao.getAllPosts());
        model.addAttribute("requestedPage", "/redactor.ftl");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/addpost")
    public String addPostButton(Model model) {
        model.addAttribute("postHeader", "");
        model.addAttribute("text", "");
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/edit")
    public String editPostButton(@RequestParam("editbyid") int id, Model model) {
        Post post = postDao.getPostById(id);
        model.addAttribute("postHeader", post.getHeader());
        model.addAttribute("text", post.getText());
        model.addAttribute("requestedPage", "/addEditPost.ftl");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/edit", method = RequestMethod.POST)
    public ModelAndView editById(@RequestParam("editbyid") int id,
                                 @RequestParam("postHeaderInput") String header,
                                 @RequestParam("postTextInput") String text,
                                 @RequestParam(value = "isPublished", required = false) String isPublised) {

        postDao.editPostById(id, header, text, isPublised != null);
        log.debug(">> Edited post with id: " + id);
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @RequestMapping(value = "/administration/postwriter/addpost", method = RequestMethod.POST)
    public ModelAndView addPost(@RequestParam("postHeaderInput") String postHeader, @RequestParam("postTextInput") String text, @RequestParam(value = "isPublished", required = false) String isPublished) {
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

    @RequestMapping(value = "/administration/postwriter", method = RequestMethod.POST)
    public ModelAndView deletePost(@RequestParam("deleteById") int id) {
        postDao.deletePostById(id);
        log.info(">> Deleted post with id: " + id);
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @RequestMapping(value = "/administration/postwriter/ajaxdelete", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxDelete(@RequestParam("deleteById") int id) {
        log.debug(">> ajax post delete id = " + id);
        postDao.deletePostById(id);
        return "deleted";
    }

}