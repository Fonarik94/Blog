package com.fonarik94.dao;

import com.fonarik94.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MySQLPostDao implements PostDao {
    private static Map<Integer, Post> cache = new HashMap<>();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Post> ROW_MAPPER = new RowMapper<Post>() {
        @Nullable
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

    public void addPost(String header, String text, boolean isPublished) {
        jdbcTemplate.update(SQLQueries.INSERT.getQueryString(), header, text, Timestamp.valueOf(LocalDateTime.now()), isPublished);
    }

    public Post getPostById(int id) {
        Post post = cache.get(id);
        if(post == null){
            log.debug("loaded from db");
            post =  jdbcTemplate.queryForObject(SQLQueries.READ_BY_ID.getQueryString(), ROW_MAPPER, id);
            cache.put(id, post);
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
        if(cache.containsKey(id)){cache.remove(id);}
            jdbcTemplate.update(SQLQueries.DELETE_BY_ID.getQueryString(), id);
    }

    public void editPostById(int id, String header, String text, boolean isPublished) {
        if(cache.containsKey(id)){cache.remove(id);}
        jdbcTemplate.update(SQLQueries.EDIT_BY_ID.getQueryString(), header, text, isPublished, id);
    }

    public List<Post> getPublishedPosts() {
        return getListOfAllPosts(true);
    }

    public List<Post> getAllPosts() {
        return getListOfAllPosts(false);
    }

    private static void createTables(Statement statement) throws SQLException {
        statement.execute(SQLQueries.CREATE_DB.getQueryString());
        statement.execute(SQLQueries.USE_DB.getQueryString());
        log.info(">> Database created...");
        statement.execute(SQLQueries.CREATE_TABLE.getQueryString());
        log.info(">> Table created...");
        statement.execute(SQLQueries.CREATE_ABOUT_PAGE.getQueryString());
        log.info(">> About page crated");
    }
}
