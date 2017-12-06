package com.fonarik94.domain;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Comment {
    private User author;
    private LocalDateTime publicationDate;
    private String text;
    private Comment answer;
}
