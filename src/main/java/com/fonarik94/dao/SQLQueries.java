package com.fonarik94.dao;

import com.fonarik94.exceptions.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SQLQueries {
    INSERT("INSERT"),
    READ_BY_ID("READ_BY_ID"),
    READ_ALL("READ_ALL"),
    READ_PUBLISHED("READ_PUBLISHED"),
    DELETE_BY_ID("DELETE_BY_ID"),
    EDIT_BY_ID("EDIT_BY_ID"),
    GET_POST_ID_BY_COMMENT_ID("GET_POST_ID_BY_COMMENT_ID"),
    DELETE_COMMENT_BY_ID("DELETE_COMMENT_BY_ID");

    private  String queryString = null;

    public String getQueryString(){
        return queryString;
    }

    SQLQueries(String query)  {
        String propertyPath = "sql/SQLQueries.xml";
        try (InputStream propXmlInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath)) {
            Properties queries = new Properties();
            queries.loadFromXML(propXmlInputStream);
            queryString = queries.getProperty(query);
        } catch (IOException e){
            throw new DAOException("Cant't use property file. Check file /WEB-INF/classes/sql/SQLQueries.xml " + e.toString());
        }
    }
}
