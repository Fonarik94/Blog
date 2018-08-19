package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class CommentController {
    @Autowired
    PostDao postDao;
    @PostMapping(value = "/post/{id:[\\d]+}/addcomment")
    public String addComment(@PathVariable("id") int postId,
                             @RequestParam("text") String text,
                             @RequestParam("author") String author){
        log.info("Post id: " + postId + "; Comment Text: "+ text);
        Comment comment = new Comment(author, text);
        postDao.addComment(postId, comment);
        return "redirect:/post/"+postId;
    }

}
