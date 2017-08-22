package com.fonarik94.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class Post {
    private int postId;
    private String postHeader;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDateTime;
    private String postText;
    private boolean isPublished;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final Logger logger = LogManager.getLogger(getCurentClassName());

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
            this.postHeader = header;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostHeader() {
        return postHeader;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCreationDateAsString() {
        return dateTimeFormat.format(creationDate);
    }

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

    public void setPublicationDateTime() {
        this.publicationDateTime = LocalDateTime.now();
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText.replaceAll(";", "\\;");
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    private Post(PostBuilder builder) {
        this.postId = builder.postId;
        this.postHeader = builder.postHeader;
        this.postText = builder.postText;
        this.creationDate = builder.creationDateTime;
        this.publicationDateTime = builder.publicationDateTime;
        this.isPublished = builder.isPublished;
    }

    public String toString(){
        return "ID: " + this.getPostId() + " Header: " + this.getPostHeader() + ";";
    }

}
