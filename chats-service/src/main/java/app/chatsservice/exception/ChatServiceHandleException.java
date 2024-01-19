package app.chatsservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ChatServiceHandleException {

    @ExceptionHandler(ChatServiceCustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleChatServiceCustomException(ChatServiceCustomException exception) {
        log.error(exception.getMessage(), exception);
    }
}
