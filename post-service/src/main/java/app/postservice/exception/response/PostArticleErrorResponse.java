package app.postservice.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostArticleErrorResponse {

    private final String errorCode;

    private final String message;

}
