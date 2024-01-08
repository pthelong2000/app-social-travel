package com.example.fileservice.exception.handler;

import com.example.fileservice.exception.custom.HandlerFileCustomException;
import com.example.fileservice.exception.response.HandlerFileErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerFileException {

    @ExceptionHandler(HandlerFileCustomException.class)
    public ResponseEntity<HandlerFileErrorResponse> handlePostArticleException(HandlerFileCustomException exception) {
        return new ResponseEntity<>(
                HandlerFileErrorResponse.builder()
                        .message(exception.getMessage())
                        .errorCode(exception.getErrorCode())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
