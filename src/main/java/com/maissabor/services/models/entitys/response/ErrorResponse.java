package com.maissabor.services.models.entitys.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    
    private Date timestamp = new Date();
    private String message;
    private String details;
    private int HttpStatus;
    private List<String> errors;

    public ErrorResponse(int HttpStatus, String message, String details, List<String> errors){
        this.HttpStatus = HttpStatus;
        this.details = details;
        this.message = message;
        this.errors = errors;
    }

}
