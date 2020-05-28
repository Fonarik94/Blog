package com.fonarik94.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fonarik94.controllers.RestApi;
import com.fonarik94.domain.Post;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Getter
public class PostResource extends RepresentationModel {
    private String header;
    private String text;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime publicationDateTime;

    public PostResource(final Post post){
        int id = post.getId();
        this.header = post.getHeader();
        this.text = post.getText();
        this.publicationDateTime = post.getPublicationDate();
        add(linkTo(methodOn(RestApi.class).getComments(id)).withRel("comments"));
        add(linkTo(methodOn(RestApi.class).index()).withRel("posts"));
        add(linkTo(methodOn(RestApi.class).get(id)).withSelfRel());
    }
}
