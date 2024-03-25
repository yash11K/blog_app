package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = IdNotFoundException.class)
    private ErrorResponse idNotFoundException(IdNotFoundException idNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDate(new Date());
        errorResponse.setMessage("no resource for the id : " + idNotFoundException.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        return errorResponse;
    }
}
