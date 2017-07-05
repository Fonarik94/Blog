package com.fonarik94.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class Post {
    private int postId;
    private String postHeader;
    private Date creationDate;
    private Date publicationDate;
    private String postText;
    boolean isPublished;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final Logger logger = LogManager.getLogger(getCurentClassName());

    public static class PostBuilder {
        private int postId = 0;
        private String postHeader = "null";
        private String postText = "null";
        private java.sql.Date creationDate;
        private java.sql.Date publicationDate;
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

        public PostBuilder setCreationDate(java.sql.Date creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public PostBuilder setPublicationDate(java.sql.Date publicationDate){
            this.publicationDate = publicationDate;
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

//    public void setPostHeader(String postHeader) {
//        this.postHeader = postHeader;
//    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreationDateAsString() {
        return dateFormat.format(creationDate);
    }

    public Date getPublicationDate() throws IllegalAccessException {
        if (isPublished) {
            return publicationDate;
        } else {
            throw new IllegalArgumentException("Post is not published");
        }
    }

    public String getPublicationDateAsString() {
        if (isPublished) {
            return dateFormat.format(publicationDate);
        } else {
            return "Not published";
        }
    }

    public void setPublicationDate() {
        this.publicationDate = new Date();
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
        postId = builder.postId;
        postHeader = builder.postHeader;
        postText = builder.postText;
        creationDate = builder.creationDate;
        publicationDate = builder.publicationDate;
        isPublished = builder.isPublished;
        if (builder.isPublished) {
            publicationDate = new Date();
        }
    }

    public String toString(){
        return "ID: " + this.getPostId() + " Header: " + this.getPostHeader() + ";";
    }

}
