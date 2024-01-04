package app.postservice.request;

import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Builder
public class PostContentRequest {

    private final String title;

    private final String content;

    private final  String author;

    @Transient
    private final MultipartFile image;

    private String imageUrl;

}
