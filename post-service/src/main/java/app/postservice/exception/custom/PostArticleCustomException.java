package app.postservice.exception.custom;

import lombok.Data;

public class PostArticleCustomException extends RuntimeException{


    public PostArticleCustomException(String message) {
        super(message);
    }
}
