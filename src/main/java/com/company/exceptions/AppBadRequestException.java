package com.company.exceptions;

public class AppBadRequestException extends RuntimeException{
    public AppBadRequestException() {
        super();
    }

    public AppBadRequestException(String message) {
        super(message);
    }
}
