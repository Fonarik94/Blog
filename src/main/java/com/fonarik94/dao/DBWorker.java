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

public class DBWorker {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    private static final String insert = "INSERT INTO posts (postHeader, text, creationDate, publicationDate, isPublished ) VALUES (?,?,CURRENT_TIMESTAMP, ?, ?)";
    private static final String readById = "SELECT idposts, postHeader, text, creationDate, publicationDate, isPublished FROM posts WHERE idposts = ?";
    private static final String readAll = "SELECT idposts, postHeader, SUBSTRING(text, 1, 500), creationDate, publicationDate, isPublished FROM posts ORDER BY idposts DESC";
    private static final String deleteById = "DELETE FROM posts WHERE idposts = ?";
    private static final String editById = "UPDATE posts SET postHeader = ?, text = ?, isPublished = ? where idposts = ?";

    private static Connection getConnection() {
        InitialContext initialContext;
        DataSource dataSource;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            Context envCont = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) envCont.lookup("jdbc/blog");
            connection = dataSource.getConnection();
            createTables(connection.createStatement());
        } catch (NamingException e) {
            logger.fatal(">> Fatal exception when creating InitialContext instance: " + e.toString());
        } catch (SQLException e) {
            logger.fatal(">> SQL: " + e.toString());
        }
        return connection;
    }



    static void addPost(Post post) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement postInsertPreparedStatement = connection.prepareStatement(insert)) {
                postInsertPreparedStatement.setString(1, post.getPostHeader());
                postInsertPreparedStatement.setString(2, post.getPostText());
                if(post.isPublished){postInsertPreparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));}
                postInsertPreparedStatement.setInt(4, post.isPublished ? 1 : 0);
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

    public static Post getPostByID(int id) {
        Post post = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement postReadByIdPreparedStatement = connection.prepareStatement(readById)) {
                postReadByIdPreparedStatement.setInt(1, id);
                ResultSet resultSet = postReadByIdPreparedStatement.executeQuery();
                while (resultSet.next()) {
                    post = new Post.PostBuilder().setPostId(resultSet.getInt("idposts"))
                            .setPostHeader(resultSet.getString("postHeader"))
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
        logger.debug(">> Post retrieved from DB: " + post.toString());
        return post;
    }

    static List<Post> getPostsAll() {
        List<Post> allPosts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement postReadAllPreparedStatement = connection.prepareStatement(readAll)) {
                ResultSet resultSet = postReadAllPreparedStatement.executeQuery();

                while (resultSet.next()) {
                    Post post = new Post.PostBuilder()
                            .setPostId(resultSet.getInt("idposts"))
                            .setPostHeader(resultSet.getString("postHeader"))
                            .setPostText(resultSet.getString(3)+"...")
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

    static void deletePostById(int id) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement deletePostByIdPreparedStatement = connection.prepareStatement(deleteById)) {
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

    static void editPostById(int id, Post updatedPost) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement editByIdPreparedStatement = connection.prepareStatement(editById)) {
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
//        statement.execute("CREATE DATABASE IF NOT EXISTS  blog CHARACTER SET  = utf8;");
        statement.execute("CREATE TABLE IF NOT EXISTS`blog`.`posts` (\n" +
                "  `idposts` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "  `postHeader` VARCHAR(256) NOT NULL,\n" +
                "  `text` TEXT NOT NULL,\n" +
                "  `creationDate` TIMESTAMP(6) NOT NULL,\n" +
                "  `publicationDate` TIMESTAMP(6),\n" +
                "  `isPublished` TINYINT NOT NULL DEFAULT 0,\n" +
                "  UNIQUE INDEX `idposts_UNIQUE` (`idposts` ASC),\n" +
                "  PRIMARY KEY (`idposts`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n");

    }
}
