package com.fonarik94.repo;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;

import java.util.List;

public interface PostDao {
    void addPost(String postHeader, String postText, boolean isPublished);
    Post getPostById(int id);
    void deletePostById(int id);
    void deleteCommentById(int id);
    void editPostById(int id, String header, String text, boolean isPublished);
    void addComment(int postId, Comment comment);
    List<Comment> getComments(int postId);
    List<Post> getPublishedPosts();
    List<Post> getAllPosts();
}
