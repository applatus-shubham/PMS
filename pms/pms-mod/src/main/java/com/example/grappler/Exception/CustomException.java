package com.example.grappler.Exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private  HttpStatus status;
    private String message;
    public CustomException(String message , HttpStatus status) {
        this.message=message;
        this.status= status;
    }
    public HttpStatus getStatus(){
        return status;
    }
    public  String getMessage()
    {
        return message;
    }
}
