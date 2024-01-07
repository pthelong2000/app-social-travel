package app.postservice.service.ipml;

import app.postservice.dto.request.PostContentRequest;
import app.postservice.entity.Images;
import app.postservice.entity.PostArticle;
import app.postservice.repository.ImageRepository;
import app.postservice.repository.PostRepository;
import app.postservice.service.PostContentService;
import app.postservice.utils.method.RequestValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostContentServiceIpml implements PostContentService {

    private final Cloudinary cloudinary;

    private final RequestValidator requestValidator;

    private final PostRepository postRepository;

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public void savePostContent(PostContentRequest postContentRequest) {


        validate(postContentRequest);

        String img = null;
        try {
            Map uploadResult = cloudinary.uploader().upload(postContentRequest.getImage().getBytes(), ObjectUtils.emptyMap());
            img = (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            log.info("Error upload images: {}", e.getMessage());
        }

        Images images = Images.builder()
                .url(img)
                .build();

        imageRepository.save(images);

        postRepository.save(PostArticle.builder()
                .title(postContentRequest.getTitle())
                .idImage(images.getId())
                .content(postContentRequest.getContent())
                .build());
    }

    private void validate(PostContentRequest postContentRequest) {
        Map<String, List<String>> errors = requestValidator.validateRequest(postContentRequest);
        if (!errors.isEmpty()) {
            throw new RuntimeException("Invalid request");
        }
    }

}
