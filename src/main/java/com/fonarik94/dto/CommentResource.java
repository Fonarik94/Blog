package com.fonarik94.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fonarik94.domain.Comment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
@Getter
public class CommentResource extends ResourceSupport {
    private String author;
    private String text;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime publicationDateTime;

    public CommentResource(Comment comment){
        this.author = comment.getAuthor();
        this.text = comment.getText();
        this.publicationDateTime = comment.getPublicationDate();
    }
}
