package com.fonarik94.dao;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@Scope("prototype")
public class JDBCPostDao implements PostDao {
    private static Map<Integer, Post> cache = new HashMap<>();
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Post> ROW_MAPPER = new RowMapper<Post>() {
        @Override
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            Post post = new Post();
            post.setId(resultSet.getInt("id"));
            post.setHeader(resultSet.getString("header"));
            post.setText(resultSet.getString("text"));
            post.setCreationDate(resultSet.getTimestamp("creationDate").toLocalDateTime());
            post.setPublicationDateTime(resultSet.getTimestamp("publicationDate").toLocalDateTime());
            post.setPublished(resultSet.getBoolean("isPublished"));
            return post;
        }
    };

    private RowMapper<Comment> COMMENT_ROW_MAPPER = new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setId(resultSet.getInt("id"));
            comment.setAuthor(resultSet.getString("author"));
            comment.setText(resultSet.getString("text"));
            comment.setPublicationDate(resultSet.getTimestamp("publicationDate").toLocalDateTime());
            return comment;
        }
    };

    @Autowired
    public JDBCPostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addPost(String header, String text, boolean isPublished) {
        jdbcTemplate.update(SQLQueries.INSERT.getQueryString(), header, text, Timestamp.valueOf(LocalDateTime.now()), isPublished);
    }

    public Post getPostById(int id) {
        Post post = cache.get(id);
        if (post == null) {
            log.debug("Post loaded from db");
            post = jdbcTemplate.queryForObject(SQLQueries.READ_BY_ID.getQueryString(), ROW_MAPPER, id);
            cache.put(id, post);
            post.addAllComments(this.getCommentsForPost(post));
        }
        return post;
    }

    private List<Post> getListOfAllPosts(boolean published) {
        List<Post> allPosts = new LinkedList<>();
        String query = published ? SQLQueries.READ_PUBLISHED.getQueryString() : SQLQueries.READ_ALL.getQueryString();
        allPosts.addAll(jdbcTemplate.query(query, ROW_MAPPER));
        return allPosts;
    }

    public void deletePostById(int id) {
        cache.remove(id);
        jdbcTemplate.update(SQLQueries.DELETE_BY_ID.getQueryString(), id);
    }

    public void deleteCommentById(int id){
        int postId = 0;
        try{
            postId= jdbcTemplate.queryForObject(SQLQueries.GET_POST_ID_BY_COMMENT_ID.getQueryString() + id, Integer.class);
        } catch (NullPointerException e){
            log.warn(">> Attempt to delete comment of not existing post\n" + e.getMessage());
        }
        log.debug("Deleting comment, post id = " + postId);
        cache.remove(postId);
        jdbcTemplate.update(SQLQueries.DELETE_COMMENT_BY_ID.getQueryString(), id);
        log.info("Deleted comment with id " + id);
    }

    public void editPostById(int id, String header, String text, boolean isPublished) {
        cache.remove(id);
        jdbcTemplate.update(SQLQueries.EDIT_BY_ID.getQueryString(), header, text, isPublished, id);
    }

    public List<Post> getPublishedPosts() {
        return getListOfAllPosts(true);
    }

    public List<Post> getAllPosts() {
        return getListOfAllPosts(false);
    }

    public void addComment(int postId, Comment comment) {
        cache.remove(postId);
        jdbcTemplate.update("INSERT INTO comments (author, text, publicationDate, post_id) values (?, ?, ?, ?)", comment.getAuthor(), comment.getText(), comment.getPublicationDate(), postId);
    }

    private List<Comment> getCommentsForPost(Post post){
        return jdbcTemplate.query("SELECT id, author, text, publicationDate FROM comments where post_id = "+post.getId(), COMMENT_ROW_MAPPER);
    }

    public List<Comment> getComments(int postId){
        return getPostById(postId).getCommentList();
    }

}
