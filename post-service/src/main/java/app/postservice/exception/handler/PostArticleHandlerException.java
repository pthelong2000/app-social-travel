package app.postservice.exception.handler;

import app.postservice.exception.custom.PostArticleCustomException;
import app.postservice.exception.response.PostArticleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostArticleHandlerException {

    @ExceptionHandler(PostArticleCustomException.class)
    public ResponseEntity<PostArticleErrorResponse> handlePostArticleException(PostArticleCustomException exception) {
        return new ResponseEntity<>(
                PostArticleErrorResponse.builder()
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_GATEWAY
        );
    }

}
