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

    public String getPublicationDateAsString() {
        if (isPublished) {
            return dateTimeFormat.format(publicationDateTime);
        } else {
            return "Not published";
        }
    }

    public Post(int id, String header, String text, boolean isPublished) {
        this.id = id;
        this.header = header;
        this.text = text;
        this.isPublished = isPublished;
    }
    public Post(){}
}
