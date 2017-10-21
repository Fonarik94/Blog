package com.fonarik94.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private int id;
    private String header;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDateTime;
    private String text;
    private boolean isPublished;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final Logger logger = LogManager.getLogger();

    public static class PostBuilder {
        private int postId = 0;
        private String postHeader = "null";
        private String postText = "null";
        private LocalDateTime creationDateTime;
        private LocalDateTime publicationDateTime;
        private boolean isPublished = false;

        public PostBuilder setPostId(int id) {
            this.postId = id;
            return this;
        }

        public PostBuilder setPostHeader(String header) {
            this.postHeader = header.replaceAll(";", "\\;");
            return this;
        }

        public PostBuilder setPostText(String text) {
            this.postText = text.replaceAll(";", "\\;");
            return this;
        }

        public PostBuilder setPublished(boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public PostBuilder setCreationDateTime(LocalDateTime creationDateTime){
            this.creationDateTime = creationDateTime;
            return this;
        }

        public PostBuilder setPublicationDateTime(LocalDateTime publicationDateTime){
            this.publicationDateTime = publicationDateTime;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

//    public String getCreationDateAsString() {
//        return dateTimeFormat.format(creationDate);
//    }

    public LocalDateTime getPublicationDateTime() {
        if (isPublished) {
            return publicationDateTime;
        } else {
            return LocalDateTime.MIN;
        }
    }

    public String getPublicationDateAsString() {
        if (isPublished) {
            return dateTimeFormat.format(publicationDateTime);
        } else {
            return "Not published";
        }
    }

//    public void setPublicationDateTime() {
//        this.publicationDateTime = LocalDateTime.now();
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text.replaceAll(";", "\\;");
    }

    public boolean isPublished() {
        return isPublished;
    }

//    public void setPublished(boolean published) {
//        isPublished = published;
//    }

    private Post(PostBuilder builder) {
        this.id = builder.postId;
        this.header = builder.postHeader;
        this.text = builder.postText;
        this.creationDate = builder.creationDateTime;
        this.publicationDateTime = builder.publicationDateTime;
        this.isPublished = builder.isPublished;
    }

    public String toString(){
        return "ID: " + this.getId() + " Header: " + this.getHeader() + ";";
    }

}
