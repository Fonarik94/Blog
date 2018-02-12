package com.fonarik94.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode
public class Comment {
    private User author;
    private LocalDateTime publicationDate;
    private String text;
    private Comment answer;
}
