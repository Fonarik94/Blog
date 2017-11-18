package com.fonarik94.dao;

import com.fonarik94.domain.Post;

import java.util.List;

public interface PostDao {
    void addPost(String postHeader, String postText, boolean isPublished);
    Post getPostById(int id);
    void deletePostById(int id);
    void editPostById(int id, String header, String text, boolean isPublished);
    List<Post> getPublishedPosts();
    List<Post> getAllPosts();
}
