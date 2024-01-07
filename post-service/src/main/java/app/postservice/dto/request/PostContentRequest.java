package app.postservice.dto.request;

import javax.persistence.Transient;

import app.postservice.validation.Content;
import app.postservice.validation.ImageSize;
import app.postservice.validation.VideoSize;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Builder
public class PostContentRequest {

    private final String title;

    @Content
    private final String content;


    @Transient
    @VideoSize
    @ImageSize
    private final MultipartFile image;

}
