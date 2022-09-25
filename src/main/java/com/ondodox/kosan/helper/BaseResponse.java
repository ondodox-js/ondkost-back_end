package com.ondodox.kosan.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse {
    protected HttpStatus httpStatus;

    public BaseResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage(){
        switch (httpStatus.value()){
            case 201:
                return "data created successfully.";
            case 302:
                return "data found.";
            case 400:
                return "bad request!.";
            case 404:
                return "data not found!.";
            case 500:
                return "internal server error!.";
            default:
                return "request successfully.";
        }
    }

    public ResponseEntity<BaseResponse> sendResponse(){
        return new ResponseEntity<>(this, httpStatus);
    }
}
