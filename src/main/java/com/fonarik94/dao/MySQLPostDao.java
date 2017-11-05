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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.fonarik94.utils.ClassNameUtil.getCurrentClassName;
//@Repository
@Component
public class MySQLPostDao implements PostDao {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Post> ROW_MAPPER = new RowMapper<Post>() {
        @Nullable
        @Override
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            Post post = new Post();
            post.setId(resultSet.getInt("id"));
            post.setHeader(resultSet.getString("header"));
            post.setText(resultSet.getString(3));
            post.setCreationDate(resultSet.getTimestamp("creationDate").toLocalDateTime());
            post.setPublicationDateTime(resultSet.getTimestamp("publicationDate").toLocalDateTime());
            post.setPublished(resultSet.getBoolean("isPublished"));
            return post;
        }
    };
    private static DataSource dataSource;
    private static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(SQLQueries.USE_DB.getQueryString());
            } catch (SQLException e) {
                logger.error(">> Cant't use DB "  + SQLQueries.DB_NAME.getQueryString() + "; Try create DB and tables...");
                try {
                    createTables(connection.createStatement());
                } catch (SQLException ce){
                    logger.fatal(">> Can't create database or something!");
                }
            }

        } catch (SQLException e) {
            logger.fatal(">> SQL: " + e.toString());
        }
        return connection;
    }

    public void addPost(String header, String text, boolean isPublished) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement postInsertPreparedStatement = connection.prepareStatement(SQLQueries.INSERT.getQueryString())) {
                postInsertPreparedStatement.setString(1, header);
                postInsertPreparedStatement.setString(2, text);
                if (isPublished) {
                    postInsertPreparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                } else {
                    postInsertPreparedStatement.setTimestamp(3, null);
                }
                postInsertPreparedStatement.setInt(4, isPublished ? 1 : 0);
                postInsertPreparedStatement.executeUpdate();
            } catch (SQLException sqlE) {
                connection.rollback();
                logger.fatal(">> Rollback: " + sqlE.toString());
            }
            connection.commit();
        } catch (SQLException ex) {
            logger.debug(">> Can't save post to DB: " + ex.toString());
        }
    }


    @Override
    public Post getPostById(int id) {
        return jdbcTemplate.queryForObject(SQLQueries.READ_BY_ID.getQueryString(), ROW_MAPPER, id);
    }

    private List<Post> getListOfAllPosts(boolean published){
        List<Post> allPosts = new LinkedList<>();
        String query = published?SQLQueries.READ_PUBLISHED.getQueryString():SQLQueries.READ_ALL.getQueryString();
        allPosts.addAll(jdbcTemplate.query(query, ROW_MAPPER));
        return allPosts;
    }

/*    public void deletePostByID(int id) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement deletePostByIdPreparedStatement = connection.prepareStatement(SQLQueries.DELETE_BY_ID.getQueryString())) {
                deletePostByIdPreparedStatement.setInt(1, id);
                deletePostByIdPreparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(">> Rollback: " + e.toString());
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error(">> Can't delete post with id " + id + ": " + e.toString());
        }

    }*/

    public void deletePostById(int id){
        jdbcTemplate.update(SQLQueries.DELETE_BY_ID.getQueryString(), id);
    }

    public void editPostById(int id, Post updatedPost) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement editByIdPreparedStatement = connection.prepareStatement(SQLQueries.EDIT_BY_ID.getQueryString())) {
                editByIdPreparedStatement.setString(1, updatedPost.getHeader());
                editByIdPreparedStatement.setString(2, updatedPost.getText());
                editByIdPreparedStatement.setInt(3, updatedPost.isPublished() ? 1 : 0);
                editByIdPreparedStatement.setInt(4, id);
                editByIdPreparedStatement.executeUpdate();
            } catch (SQLException ex) {
                logger.fatal("Rollback: " + ex.toString());
            }
            connection.commit();
        } catch (SQLException e) {

            logger.fatal(">> Cant't update post with ID: " + id + "; " + e.toString());
        }
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
