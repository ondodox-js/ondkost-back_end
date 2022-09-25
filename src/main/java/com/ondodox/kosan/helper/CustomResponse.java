package com.ondodox.kosan.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse extends ResponseEntity<Object> {

    public CustomResponse(SuccessResponse response, HttpStatus status){
        super(response, status);
    }
}
