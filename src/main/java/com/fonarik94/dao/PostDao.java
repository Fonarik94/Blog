package com.fonarik94.dao;

import java.util.List;
import java.util.Map;

public interface PostDao {
    void addPost(String postHeader, String postText, boolean isPublished);
    Post getPostById(int id);
    void deletePostByID(int id);
    void editPostById(int id, Post editedPost);
    List<Post> getListOfAllPosts(boolean published);
}
