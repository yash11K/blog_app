package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.exception.NoSearchResults;
import com.mountblue.blogapp.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NoSearchResults.class)
    public ErrorResponse handleNoSearchException(NoSearchResults noSearchResults){
        ErrorResponse noResultErrorResponse = new ErrorResponse();
        noResultErrorResponse.setDate(new Date());
        noResultErrorResponse.setMessage(noResultErrorResponse.getMessage());
        noResultErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        return noResultErrorResponse;
    }
}
