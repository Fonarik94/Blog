package com.fonarik94.dao;

import com.fonarik94.domain.Post;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.fonarik94.utils.ClassNameUtil.getCurrentClassName;

@Component
public class MySQLPostDao implements PostDao {
    private static final Logger logger = LogManager.getLogger();
    private static Map<Integer, Post> cache = new IdentityHashMap<>();
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

    @Override
    public Post getPostById(int id) {
        Post post = cache.get(id);
        if(post == null){
            logger.debug("loaded from db");
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

    @Override
    public List<Post> getPublishedPosts() {
        return getListOfAllPosts(true);
    }

    @Override
    public List<Post> getAllPosts() {
        return getListOfAllPosts(false);
    }

    private static void createTables(Statement statement) throws SQLException {
        statement.execute(SQLQueries.CREATE_DB.getQueryString());
        statement.execute(SQLQueries.USE_DB.getQueryString());
        logger.info(">> Database created...");
        statement.execute(SQLQueries.CREATE_TABLE.getQueryString());
        logger.info(">> Table created...");
        statement.execute(SQLQueries.CREATE_ABOUT_PAGE.getQueryString());
        logger.info(">> About page crated");
    }
}
