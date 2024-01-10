package app.postservice.dto.request;

import app.postservice.utils.validation.Content;
import app.postservice.utils.validation.ImageSize;
import app.postservice.utils.validation.VideoSize;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;

@Getter
@Builder
public class UpdatePostArticleRequest {

    private final Long id;

    private final String title;

    @Content
    private final String content;

    @Transient
    @VideoSize
    @ImageSize
    private final MultipartFile image;
}
