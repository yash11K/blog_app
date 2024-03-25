package com.mountblue.blogapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.Date;

@Setter
@Getter
public class ErrorResponse {
    private HttpStatusCode statusCode;
    private String message;
    private Date date;
}
