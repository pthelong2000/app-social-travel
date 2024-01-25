package app.chatsservice.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    @JsonProperty("error_code")
    private final String errorCode;

    @JsonProperty("message")
    private final String message;
}
