package app.chatsservice.exception.handler;

import app.chatsservice.exception.custom.ChatServiceCustomException;
import app.chatsservice.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ChatServiceHandleException {

    @ExceptionHandler(ChatServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleChatServiceCustomException(ChatServiceCustomException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .errorCode("400")
                .message(exception.getMessage())
                .build());
    }
}
