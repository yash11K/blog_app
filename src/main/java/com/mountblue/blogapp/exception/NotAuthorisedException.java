package com.mountblue.blogapp.exception;

public class NotAuthorisedException extends RuntimeException{
    public NotAuthorisedException() {
    }

    public NotAuthorisedException(String message) {
        super(message);
    }
}
