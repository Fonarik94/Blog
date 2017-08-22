package com.fonarik94.dao;

class DAOException extends Error {
    String message;
    DAOException(String message){
        super(message);
    }
}
