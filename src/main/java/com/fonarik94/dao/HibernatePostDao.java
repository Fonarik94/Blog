package com.fonarik94.dao;

import com.fonarik94.domain.Post;

import java.util.List;

public class HibernatePostDao implements PostDao{
    @Override
    public void addPost(String postHeader, String postText, boolean isPublished) {

    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public void deletePostById(int id) {

    }

    @Override
    public void editPostById(int id, String header, String text, boolean isPublished) {

    }

    @Override
    public List<Post> getPublishedPosts() {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }
}
