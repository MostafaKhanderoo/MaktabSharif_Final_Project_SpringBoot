package com.example.maktabsharif.homeservices.exception;

public class ExistsException extends RuntimeException {
    private final CustomApiExceptionType type;
    public ExistsException(CustomApiExceptionType type) {
        super();
        this.type =type;

    }


    public ExistsException(String message,CustomApiExceptionType type) {
        super(message   );
        this.type =type;
    }

    public CustomApiExceptionType getType() {
        return this.type;
    }
}
