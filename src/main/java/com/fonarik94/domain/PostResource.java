package com.fonarik94.domain;

import java.util.ArrayList;
import java.util.List;

public class PostResource {
    private final int id;
    private final String publicationDateTime;
    private final String header;
    private final String text;

    public PostResource(Post post){
        this.id = post.getId();
        this.publicationDateTime = post.getPublicationDateAsString();
        this.header = post.getHeader();
        this.text = post.getText();
    }


    public int getId() {
        return id;
    }

    public String getPublicationDateTime() {
        return publicationDateTime;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }


}
