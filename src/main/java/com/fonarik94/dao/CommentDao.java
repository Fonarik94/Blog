package com.fonarik94.dao;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;

import java.util.List;

public interface CommentDao {
    Comment getById(int id);
    void deleteById(int id);
    void create();
    List<Comment> getAllComments(Post post);
}
