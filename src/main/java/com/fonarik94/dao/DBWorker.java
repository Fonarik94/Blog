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
    private static final String DBNAME = "blog";
    private static final String INSERT = "INSERT INTO posts (header, text, creationDate, publicationDate, isPublished ) VALUES (?,?,CURRENT_TIMESTAMP, ?, ?)";
    private static final String READ_BY_ID = "SELECT id, header, text, creationDate, publicationDate, isPublished FROM posts WHERE id = ?";
    private static final String READ_ALL = "SELECT id, header, SUBSTRING(text, 1, 500), creationDate, publicationDate, isPublished FROM posts ORDER BY id DESC";
    private static final String DELETE_BY_ID = "DELETE FROM posts WHERE id = ?";
    private static final String EDIT_BY_ID = "UPDATE posts SET header = ?, text = ?, isPublished = ? where id = ?";

    private static Connection getConnection() {
        InitialContext initialContext;
        DataSource dataSource;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            Context envCont = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) envCont.lookup("jdbc/blog");
            connection = dataSource.getConnection();
            try(Statement statement = connection.createStatement()){
                statement.execute("USE " + DBNAME );
            }
            catch (SQLException e){
                logger.info(">> Cant't use DB " + "\"" + DBNAME + "\"; Try create DB and tables...");
                createTables(connection.createStatement());
            }


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
            try (PreparedStatement postInsertPreparedStatement = connection.prepareStatement(INSERT)) {
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
            try (PreparedStatement postReadByIdPreparedStatement = connection.prepareStatement(READ_BY_ID)) {
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

    static List<Post> getPostsAll() {
        List<Post> allPosts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement postReadAllPreparedStatement = connection.prepareStatement(READ_ALL)){
                ResultSet resultSet = postReadAllPreparedStatement.executeQuery();
                while (resultSet.next()) {
                    Post post = new Post.PostBuilder()
                            .setPostId(resultSet.getInt("id"))
                            .setPostHeader(resultSet.getString("header"))
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
            try (PreparedStatement deletePostByIdPreparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
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
            try (PreparedStatement editByIdPreparedStatement = connection.prepareStatement(EDIT_BY_ID)) {
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
        statement.execute("CREATE DATABASE IF NOT EXISTS `blog` CHARACTER SET  = utf8;");
        statement.execute("USE " + DBNAME );
        statement.execute("CREATE TABLE IF NOT EXISTS `posts` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `header` varchar(256) NOT NULL,\n" +
                "  `text` text NOT NULL,\n" +
                "  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `publicationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
                "  `isPublished` tinyint(4) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `idposts_UNIQUE` (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;");

    }
}
