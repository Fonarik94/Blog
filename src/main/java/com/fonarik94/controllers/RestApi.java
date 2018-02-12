package com.fonarik94.controllers;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Post;
import com.fonarik94.domain.PostResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class RestApi {

    @Autowired
    PostDao postDao;

    @GetMapping(value = "/posts.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResource> getPublishedPosts(){
        log.debug(">> JSON request");
        List<PostResource> postResourceList = new ArrayList<>();
        for (Post post: postDao.getPublishedPosts()) {
            postResourceList.add(new PostResource(post));
        }
    return postResourceList;
    }

    @GetMapping(value = ("/post/{id:[\\d]+}.json"), produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public PostResource getPost(@PathVariable("id") int id){
        return new PostResource(postDao.getPostById(id));
    }

}
