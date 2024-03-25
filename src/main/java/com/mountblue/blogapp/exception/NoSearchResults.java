package com.mountblue.blogapp.exception;

public class NoSearchResults extends RuntimeException{
    public NoSearchResults(){
        super();
    }
    public NoSearchResults(String message) {
        super(message);
    }
}
