package app.postservice.service.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    @JsonProperty(namespace = "content")
    private final String content;

    @JsonProperty(namespace = "author")
    private final String author;

    @JsonProperty(namespace = "date")
    private final String date;


}
