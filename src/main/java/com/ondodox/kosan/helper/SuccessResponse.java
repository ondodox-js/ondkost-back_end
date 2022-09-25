package com.ondodox.kosan.helper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Slf4j
public class SuccessResponse extends BaseResponse{
    protected boolean status = true;
    protected String message;
    public Object payload;

    public SuccessResponse(Object payload, HttpStatus httpStatus) {
        super(httpStatus);
        this.payload = payload;
        this.message = super.getMessage();
    }

}
