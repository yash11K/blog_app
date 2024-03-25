package com.mountblue.blogapp.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String message) {
        super(message);
    }
}
