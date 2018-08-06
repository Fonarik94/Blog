package com.fonarik94.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Comment {
    private int id;
    //private User author;
    private LocalDateTime publicationDate;
    private String text;
    private Comment answer;
}
