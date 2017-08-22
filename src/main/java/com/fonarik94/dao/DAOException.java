package com.fonarik94.dao;

public class DAOException extends Error {
    String message;
    public DAOException(String message){
        super(message);
    }
}
