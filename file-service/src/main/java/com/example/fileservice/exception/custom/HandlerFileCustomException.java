package com.example.fileservice.exception.custom;

import lombok.Getter;

@Getter
public class HandlerFileCustomException extends RuntimeException{

    private final String errorCode;

    public HandlerFileCustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
