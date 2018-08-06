package com.fonarik94.controllers;

import com.fonarik94.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class CommentController {

    @PostMapping(value = "/post/{id:[\\d]+}/addcomment")
    @ResponseBody
    public String addComment(@PathVariable("id") int postId, @RequestParam("commentText") String text){
        log.info("Post id: " + postId + "; Comment Text: "+ text);
        Comment comment = new Comment(42, LocalDateTime.now(), text, null);
        return text;
    }

}
