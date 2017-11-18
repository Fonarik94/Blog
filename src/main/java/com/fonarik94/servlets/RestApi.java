package com.fonarik94.servlets;

import com.fonarik94.dao.PostDao;
import com.fonarik94.domain.Post;
import com.fonarik94.domain.PostResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApi {
    private static Logger log = LogManager.getLogger();

    @Autowired
    PostDao postDao;

    @RequestMapping(value = "/posts.json", produces = MediaType.APPLICATION_JSON_VALUE , method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResource> getPublishedPosts(){
        log.debug(">> JSON request");
        List<PostResource> postResourceList = new ArrayList<>();
        for (Post post: postDao.getPublishedPosts()) {
            postResourceList.add(new PostResource(post));
        }
    return postResourceList;
    }

    @RequestMapping(value = ("/post/{id}.json"), produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public PostResource getPost(@PathVariable("id") int id){
        return new PostResource(postDao.getPostById(id));
    }

}
