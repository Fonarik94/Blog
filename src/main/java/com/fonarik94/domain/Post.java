package com.fonarik94.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class Post {
    private int id;
    private String header;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDateTime;
    private String text;
    private boolean isPublished;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
}
