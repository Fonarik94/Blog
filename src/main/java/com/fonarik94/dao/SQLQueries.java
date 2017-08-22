package com.fonarik94.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SQLQueries {
    DB_NAME("DB_NAME"),
    INSERT("INSERT"),
    READ_BY_ID("READ_BY_ID"),
    READ_ALL("READ_ALL"),
    READ_PUBLISHED("READ_PUBLISHED"),
    DELETE_BY_ID("DELETE_BY_ID"),
    EDIT_BY_ID("EDIT_BY_ID"),
    USE_DB("USE_DB"),
    CREATE_DB("CREATE_DB"),
    CREATE_TABLE("CREATE_TABLE"),
    CREATE_ABOUT_PAGE("CREATE_ABOUT_PAGE");
    private final String propertyPath = "sql/SQLQueries.xml";
    private  String queryString = null;
    private  Properties queries = null;

    public String getQueryString(){
        return queryString;
    }

    SQLQueries(String query)  {
        try (InputStream propXmlInputStraem = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath)) {
            queries = new Properties();
            queries.loadFromXML(propXmlInputStraem);
            queryString = queries.getProperty(query);
        } catch (IOException e){
            throw new DAOException("Cant't use property file. Check file /WEB-INF/classes/sql/SQLQueries.xml " + e.toString());
        }
    }
}
