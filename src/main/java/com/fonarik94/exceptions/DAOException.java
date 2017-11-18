package com.fonarik94.exceptions;

public class DAOException extends RuntimeException {
    String message;
    public DAOException(String message){
        super(message);
    }
}
