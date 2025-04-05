package com.example.maktabsharif.homeservices.exception;

public class InvalidInputException extends RuntimeException {
    private  final  CustomApiExceptionType type;

    public InvalidInputException(String message,CustomApiExceptionType type) {
        super(message);
        this.type = type;
    }
}
