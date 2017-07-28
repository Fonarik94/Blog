package com.fonarik94.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class PostDaoImpl implements PostDao {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    private static PostDao postDao;
    private static int postId = 0;

    public static synchronized PostDao getInstance() {
        if (postDao != null) {
            return postDao;
        }
        postDao = new PostDaoImpl();
        logger.debug(">> Singleton created");
        return postDao;
    }

    Map<Integer, Post> postMap = new ConcurrentHashMap<>();

    private PostDaoImpl() {
    }

    @Override
    public void addPost(String header, String text, boolean isPublished) {
        Post post = new Post.PostBuilder()
                .setPostId(postId++)
                .setPostHeader(header)
                .setPostText(text)
                .setPublished(isPublished)
                .build();
        DBWorker.addPost(post);

    }

    @Override
    public Post getPostById(int postID) {
        return DBWorker.getPostByID(postID);
    }

    @Override
    public Map getPosts(int start, int end) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    public List<Post> getListOfPosts(int start, int end) {
        List<Post> postList = new LinkedList<>();
        Post post;
        int count = 0;
        for (int i = start; i < end; i++) {
            post = postMap.get(i);
            if (post != null) {
                postList.add(post);
                count++;
            }
        }
        return postList;

    }

    public List<Post> getListOfAllPosts(boolean publishedOnly) {
        List<Post> listFromDB = new ArrayList<>();
        List<Post> resultList = new ArrayList<>();
        listFromDB.addAll(DBWorker.getPostsAll());
        if (publishedOnly) {
            for (Post p : listFromDB) {
                if (p.isPublished) {
                    resultList.add(p);
                }
            }
        } else {
            resultList = listFromDB;
        }
        return resultList;
    }

    public void deletePostByID(int postId) {
        DBWorker.deletePostById(postId);
    }

    public void editPostById(int postId, Post editedPost){
        DBWorker.editPostById(postId, editedPost);
    }
}
