package app.chatsservice.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final String errorCode;
    private final String message;
}
