package com.ondodox.kosan.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse extends BaseResponse{
    protected boolean status = false;
    protected String message;
    private List<String> error = new LinkedList<>();


    public ErrorResponse(Exception exception, HttpStatus httpStatus) {
        super(httpStatus);
        if (exception.getMessage() != null){
            error.add(exception.getMessage());
        }
        this.message = super.getMessage();
    }
}
