package app.postservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostContentResponse {

    @JsonProperty(namespace = "title")
    private final String title;

    @JsonProperty(namespace = "content")
    private final String content;

    @JsonProperty(namespace = "image")
    private final String image;

    @JsonProperty(namespace = "createdAt")
    private final String createdAt;

    @JsonProperty(namespace = "createdBy")
    private final String createdBy;

    @JsonProperty(namespace = "updatedAt")
    private final String updatedAt;

    @JsonProperty(namespace = "updatedBy")
    private final String updatedBy;
}
