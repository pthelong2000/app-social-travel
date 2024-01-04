package app.postservice.exception.custom;

import lombok.Data;

@Data
public class PostArticleCustomException extends RuntimeException{


    public PostArticleCustomException(String message) {
        super(message);
    }
}
