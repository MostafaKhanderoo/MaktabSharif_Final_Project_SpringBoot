package com.example.maktabsharif.homeservices.exception;

public class NotFoundException extends RuntimeException{
    private final CustomApiExceptionType type;

    public NotFoundException(CustomApiExceptionType type) {
        super();
        this.type =type;
    }

    public NotFoundException(String message,CustomApiExceptionType type) {
        super(message);
        this.type =type;
    }

}
