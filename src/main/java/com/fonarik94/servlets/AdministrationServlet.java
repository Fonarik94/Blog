package com.fonarik94.servlets;

import com.fonarik94.dao.Post;
import com.fonarik94.dao.PostDao;
import com.fonarik94.utils.WakeOnLan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrationServlet {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    PostDao postDao;

    @RequestMapping(value = "/administration")
    public String wol(Model model) {
        model.addAttribute("requestedPage", "/jsp/wol.jsp");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter")
    public String postWriter(Model model) {
        model.addAttribute("postDao", postDao);
        model.addAttribute("requestedPage", "/jsp/redactor.jsp");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/addpost")
    public String addPostButton(Model model) {
        model.addAttribute("postHeader", "");
        model.addAttribute("text", "");
        model.addAttribute("mode", "add");
        model.addAttribute("requestedPage", "/jsp/addEditPost.jsp");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/edit")
    public String editPostButton(@RequestParam("editbyid") int id, Model model) {
        Post post = postDao.getPostById(id);
        model.addAttribute("postHeader", post.getPostHeader());
        model.addAttribute("text", post.getPostText());
        model.addAttribute("mode", "edit");
        model.addAttribute("requestedPage", "/jsp/addEditPost.jsp");
        return "administration";
    }

    @RequestMapping(value = "/administration/postwriter/edit", method = RequestMethod.POST)
    public ModelAndView editById(@RequestParam("editbyid") int id,
                                 @RequestParam("postHeaderInput") String header,
                                 @RequestParam("postTextInput") String text,
                                 @RequestParam("isPublished") String isPublised,
                                 Model model) {
        Post editedPost = new Post.PostBuilder()
                .setPostHeader(header)
                .setPostText(text)
                .setPublished(isPublised != null)
                .setCreationDateTime(null)
                .setPublicationDateTime(null)
                .setPostId(id)
                .build();
        postDao.editPostById(id, editedPost);

        return new ModelAndView("redirect:/administration/postwriter");
    }

    @RequestMapping(value = "/administration/postwriter/addpost", method = RequestMethod.POST)
    public ModelAndView addPost(@RequestParam("postHeaderInput") String postHeader, @RequestParam("postTextInput") String text, @RequestParam("isPublished") String isPublished) {
        boolean published;
        published = isPublished != null;
        postDao.addPost(postHeader, text, published);
        log.debug(">> Post added");
        return new ModelAndView("redirect:/administration/postwriter");
    }

    @RequestMapping(value = "/administration", method = RequestMethod.POST)
    public ModelAndView wolSender(@RequestParam("mac") String mac){
        WakeOnLan.wake(mac);
        log.debug("Wake-on-lan request. MAC: " + mac);
        return new ModelAndView("redirect:/administration");
    }
    @RequestMapping(value = "/administration/postwriter", method = RequestMethod.POST)
    public ModelAndView deletePost(@RequestParam("deleteById") int id){
        postDao.deletePostByID(id);
        log.info(">> Deleted post with id: " + id);
        return new ModelAndView("redirect:/administration/postwriter");
    }
}