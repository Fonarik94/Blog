package com.fonarik94.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class PostDaoImpl implements PostDao {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());

    private static Connection getConnection() {
        InitialContext initialContext;
        DataSource dataSource;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            Context envCont = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) envCont.lookup("jdbc/blog");
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

        } catch (NamingException e) {
            logger.fatal(">> Fatal exception when creating InitialContext instance: " + e.toString());
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

    public Post getPostById(int id) {
        Post post = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement postReadByIdPreparedStatement = connection.prepareStatement(SQLQueries.READ_BY_ID.getQueryString())) {
                postReadByIdPreparedStatement.setInt(1, id);
                ResultSet resultSet = postReadByIdPreparedStatement.executeQuery();
                while (resultSet.next()) {
                    post = new Post.PostBuilder().setPostId(resultSet.getInt("id"))
                            .setPostHeader(resultSet.getString("header"))
                            .setPostText(resultSet.getString("text"))
                            .setCreationDateTime(resultSet.getTimestamp("creationDate").toLocalDateTime())
                            .setPublicationDateTime(resultSet.getTimestamp("publicationDate").toLocalDateTime())
                            .setPublished(resultSet.getBoolean("isPublished"))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.fatal(">> Can't select post with id = " + id + ": " + e.toString());
        }
//        logger.debug(">> Post retrieved from DB: " + post.toString());
        return post;
    }

    public List<Post> getListOfAllPosts(boolean published)  {
        List<Post> allPosts = new ArrayList<>();
        String query = published?SQLQueries.READ_PUBLISHED.getQueryString():SQLQueries.READ_ALL.getQueryString();
        try (Connection connection = getConnection()) {
            try (PreparedStatement postReadAllPreparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = postReadAllPreparedStatement.executeQuery();
                while (resultSet.next()) {
                    Post post = new Post.PostBuilder()
                            .setPostId(resultSet.getInt("id"))
                            .setPostHeader(resultSet.getString("header"))
                            .setPostText(resultSet.getString(3) + "...")
                            .setCreationDateTime(resultSet.getTimestamp("creationDate").toLocalDateTime())
                            .setPublicationDateTime(resultSet.getTimestamp("publicationDate").toLocalDateTime())
                            .setPublished((resultSet.getInt("isPublished")) == 1)
                            .build();
                    allPosts.add(post);
                }
            }
        } catch (SQLException e) {
            logger.fatal("Can't read all posts : " + e.toString());
        }

        return allPosts;
    }

    public void deletePostByID(int id) {
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

    }

    public void editPostById(int id, Post updatedPost) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement editByIdPreparedStatement = connection.prepareStatement(SQLQueries.EDIT_BY_ID.getQueryString())) {
                editByIdPreparedStatement.setString(1, updatedPost.getPostHeader());
                editByIdPreparedStatement.setString(2, updatedPost.getPostText());
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
