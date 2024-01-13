package app.postservice.service.ipml;

import app.postservice.dto.request.CreatePostArticleRequest;
import app.postservice.dto.request.UpdatePostArticleRequest;
import app.postservice.entity.Images;
import app.postservice.entity.PostArticle;
import app.postservice.exception.custom.PostArticleCustomException;
import app.postservice.repository.ImageRepository;
import app.postservice.repository.PostRepository;
import app.postservice.service.PostArticleService;
import app.postservice.utils.Constants;
import app.postservice.utils.enums.StatusPost;
import app.postservice.utils.message.MessageUtils;
import app.postservice.utils.method.RequestValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostArticleServiceImpl implements PostArticleService {

    private final Cloudinary cloudinary;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final RequestValidator requestValidator;

    private final KafkaTemplate<Long, CreatePostArticleRequest> kafkaTemplate;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;

    @Value("${kafka.topic.posts}")
    private String postsTopic;


    @Override
    @Transactional
    public void savePostContent(CreatePostArticleRequest createPostArticleRequest) {
        validateCreatePostArticle(createPostArticleRequest);
        String imageUrl = uploadImage(createPostArticleRequest.getImage());
        Images images = imageRepository.save(Images.builder().url(imageUrl).build());
        postRepository.save(PostArticle.builder()
                .title(createPostArticleRequest.getTitle())
                .idImage(images.getId())
                .content(createPostArticleRequest.getContent())
                .status(StatusPost.PUBLISHED.getValue())
                .build());
        kafkaTemplate.send(new ProducerRecord<>(postsTopic, createPostArticleRequest));
    }

    @Override
    @Transactional
    public void updatePostContent(UpdatePostArticleRequest request) {
        validateUpdatePostArticle(request);
        PostArticle postArticle = postRepository.findById(request.getId())
                .orElseThrow(() -> new PostArticleCustomException(MessageUtils.getMessage(Constants.NOT_FOUND)));
        String imageUrl = uploadImage(request.getImage());
        Images images = imageRepository.save(Images.builder().url(imageUrl).build());
        postRepository.save(PostArticle.builder()
                .id(postArticle.getId())
                .title(request.getTitle())
                .idImage(images.getId())
                .content(request.getContent())
                .build());
    }

    public String uploadImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            log.error("Error while uploading image: {}", e.getMessage());
            throw new PostArticleCustomException(MessageUtils.getMessage(Constants.UPLOAD_IMAGE_FAILED));
        }
    }

    @Override
    public PostArticle findPostArticleById(Long id) {
       Optional<PostArticle> postArticle = postRepository.findById(id);
         if(postArticle.isPresent()){
              log.info("PostArticle Not Found With ID: {}", id);
         }
        return postArticle.get();
    }

    private void validateCreatePostArticle(CreatePostArticleRequest request) {
        Map<String, List<String>> errors = requestValidator.validateRequest(request);
        if (StringUtils.isEmpty(request.getContent())) {
            errors.put("content", List.of(MessageUtils.getMessage(Constants.TITLE_NOT_FOUND)));
        }
    }

    private void validateUpdatePostArticle(UpdatePostArticleRequest updatePostArticleRequest) {
        requestValidator.validateRequest(updatePostArticleRequest);
    }


}