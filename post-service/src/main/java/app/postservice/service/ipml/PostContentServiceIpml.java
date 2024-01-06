package app.postservice.service.ipml;

import app.postservice.entity.Images;
import app.postservice.entity.Post_Article;
import app.postservice.exception.custom.PostArticleCustomException;
import app.postservice.repository.ImageRepository;
import app.postservice.repository.PostCustomRepository;
import app.postservice.repository.PostRepository;
import app.postservice.dto.request.PostContentRequest;
import app.postservice.service.PostContentService;
import app.postservice.utils.Constants;
import app.postservice.utils.message.MessageUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostContentServiceIpml implements PostContentService {

    private final PostRepository postRepository;

    private final MessageUtils messageUtils;

    private final PostCustomRepository postCustomRepository;

    private final ImageRepository imageRepository;

    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public void savePostContent(PostContentRequest postContentRequest) {

        if (Objects.nonNull(postContentRequest.getTitle())
                && Objects.nonNull(postContentRequest.getAuthor())
                && Objects.nonNull(postContentRequest.getContent())
                && Objects.nonNull(postContentRequest.getImage())) {
            throw new PostArticleCustomException(messageUtils.getMessage(Constants.UPLOAD_IMG_FAILED));
        }
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

        postRepository.save(Post_Article.builder()
                .title(postContentRequest.getTitle())
                .author(postContentRequest.getAuthor())
                .idImage(images.getId())
                .content(postContentRequest.getContent())
                .build());

    }
}
