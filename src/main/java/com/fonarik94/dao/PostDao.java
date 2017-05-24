package com.fonarik94.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public  class PostDao {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    static PostDao post = null;
    private String postHeader = "Test post header";
    private Date postPublicationDate;

    private String postText = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor\" +\n" +
            "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco \" +\n" +
            "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\\n\" +\n" +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\\n\" +\n" +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\\n\" +\n" +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\\n\" +\n" +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\\n\" +\n" +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit ess\n" +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non \n" +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";


    public static synchronized PostDao getInstance() {
        if (post != null) {
            return post;
        }
        post = new PostDao();
        logger.debug(">> Singleton created");
        return post;
    }
    public String getPostHeader(){
        return this.postHeader;
    }
    public String getPublicationDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.postPublicationDate = new Date();
        return dateFormat.format(this.postPublicationDate);
    }
    public String getPostText(){
        return postText;
    }

    public List getList(){
        List<PostDao> allPosts = new LinkedList<>();
        for (int i = 0; i <= 10; i++){
            allPosts.add(post);
        }
        logger.debug(">> List of posts created");
        return allPosts;
    }

    private PostDao(){}
}
