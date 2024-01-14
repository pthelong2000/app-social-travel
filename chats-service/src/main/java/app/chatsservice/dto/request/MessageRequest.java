package app.chatsservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MessageRequest {

    @JsonProperty("conversation_id")
    private String content;

    @JsonProperty("file_data")
    private MultipartFile fileData;
}
