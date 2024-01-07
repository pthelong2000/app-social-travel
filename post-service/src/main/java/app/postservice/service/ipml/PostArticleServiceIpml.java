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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostArticleServiceIpml implements PostArticleService {


    private final Cloudinary cloudinary;

    private final MessageUtils messageUtils;

    private final PostRepository postRepository;

    private final ImageRepository imageRepository;

    private final RequestValidator requestValidator;

    @Override
    @Transactional
    public void savePostContent(CreatePostArticleRequest createPostArticleRequest) {


        validateCreatePostArticle(createPostArticleRequest);

        Images images = Images.builder()
                .url(uploadImage(createPostArticleRequest.getImage()))
                .build();

        imageRepository.save(images);

        postRepository.save(PostArticle.builder()
                .title(createPostArticleRequest.getTitle())
                .idImage(images.getId())
                .content(createPostArticleRequest.getContent())
                .status(StatusPost.PUBLISHED.getValue())
                .build());
    }

    @Override
    public void updatePostContent(UpdatePostArticleRequest request) {

        validateUpdatePostArticle(request);

        PostArticle postArticle = postRepository.findById(request.getId())
                .orElseThrow(
                        () -> new PostArticleCustomException(MessageUtils.getMessage(Constants.UPLOAD_IMG_FAILED)));


        Images images = Images.builder()
                .url(uploadImage(request.getImage()))
                .build();

        imageRepository.save(images);

        postRepository.save(PostArticle.builder()
                .title(request.getTitle())
                .idImage(images.getId())
                .content(request.getContent())
                .build());
    }

    private String uploadImage(MultipartFile fileUpload){
        String img = null;

        try {
            Map uploadResult = cloudinary.uploader()
                    .upload(fileUpload.getBytes(), ObjectUtils.emptyMap());
            img = (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            log.info(messageUtils.getMessage(Constants.UPLOAD_IMG_FAILED));
        }

        return img;
    }


    private void validateCreatePostArticle(CreatePostArticleRequest createPostArticleRequest) {
        Map<String, List<String>> errors = requestValidator.validateRequest(createPostArticleRequest);
        if(org.springframework.util.ObjectUtils.isEmpty(createPostArticleRequest.getContent())){
            errors.put("content", List.of(messageUtils.getMessage(Constants.TITLE_NOT_FOUND)));
        }
    }

    private void validateUpdatePostArticle(UpdatePostArticleRequest updatePostArticleRequest) {
        Map<String, List<String>> errors = requestValidator.validateRequest(updatePostArticleRequest);
    }

}
