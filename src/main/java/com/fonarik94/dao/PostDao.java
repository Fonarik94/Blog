package com.fonarik94.dao;

import java.util.Map;

public interface PostDao {
    void addPost(String postHeader, String postText, boolean isPublished);
    Post getPostById(int postID);
    Map getPosts(int start, int end);
    void deletePostByID(int id);
    void editPostById(int id, Post editedPost);
}
