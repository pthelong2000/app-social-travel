package com.example.fileservice.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HandlerFileErrorResponse {

    private final String errorCode;

    private final String message;

}
