package com.fonarik94.dao;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCCommentDao implements CommentDao {
    private Map<Integer, Comment> cache = new HashMap<>();

    @Override
    public Comment getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void create() {

    }

    @Override
    public List<Comment> getAllComments(Post post) {
        return null;
    }
}
