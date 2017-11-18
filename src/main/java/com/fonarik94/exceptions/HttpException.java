package com.fonarik94.exceptions;

import lombok.Data;

@Data
public class HttpException {
    private int errorCode;
    private String errorMessage;
}
