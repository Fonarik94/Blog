package com.fonarik94.controllers;

import com.fonarik94.domain.Post;
import com.fonarik94.dto.CommentResource;
import com.fonarik94.dto.PostResource;
import com.fonarik94.exceptions.ResourceNotFoundException;
import com.fonarik94.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping(value = "/api")
public class RestApi {
    private final PostRepository postRepository;

    @GetMapping("posts")
    public ResponseEntity index() {
        Link self = linkTo(methodOn(RestApi.class).index()).withSelfRel();
        List<Link> links = new ArrayList<>();
        postRepository.findPublished()
                .forEach(post -> {
                    links.add(linkTo(methodOn(RestApi.class).get(post.getId())).withRel(post.getHeader()));
                });
        links.add(self);
        return ResponseEntity.ok(links);
    }

    @GetMapping("post/{id:[\\d]+}")
    public ResponseEntity<PostResource> get(@PathVariable("id") int id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        PostResource postResource = new PostResource(post);
        return ResponseEntity.ok(postResource);
    }

    @GetMapping("/post/{id:[\\d]+}/comments")
    public ResponseEntity<List<CommentResource>> getComments(@PathVariable("id") int id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<CommentResource> resourceList = post.getComments()
                .stream()
                .map(CommentResource::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resourceList);
    }


    @Autowired
    public RestApi(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

}
