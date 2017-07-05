package com.fonarik94.dao;

public class DAOException extends Exception {
    String message;
    public DAOException(String message){
        super(message);
    }
}
