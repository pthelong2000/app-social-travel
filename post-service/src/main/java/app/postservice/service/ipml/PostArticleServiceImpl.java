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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostArticleServiceImpl implements PostArticleService {

    private final Cloudinary cloudinary;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final RequestValidator requestValidator;

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

    private String uploadImage(MultipartFile fileUpload) {
        try {
            Map uploadResult = cloudinary.uploader().upload(fileUpload.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            log.info(MessageUtils.getMessage(Constants.UPLOAD_FILE_FAILED));
            throw new RuntimeException("Failed to upload image.", e);
        }
    }

    private void validateCreatePostArticle(CreatePostArticleRequest request) {
        Map<String, List<String>> errors = requestValidator.validateRequest(request);
        if (StringUtils.isEmpty(request.getContent())) {
            errors.put("content", List.of(MessageUtils.getMessage(Constants.TITLE_NOT_FOUND)));
        }
        String fileExtension =
                getFileExtension(Objects.requireNonNull(request.getImage().getOriginalFilename()));
        long imageSize = request.getImage().getSize();
        if (isImageFile(fileExtension)) {
            if (imageSize > 1024 * 1024 * 10) {
                errors.put("image", List.of(MessageUtils.getMessage(Constants.IMAGE_SIZE_INVALID)));
            }
        } else if (isVideoFile(fileExtension)) {
            if (imageSize > 1024 * 1024 * 500) {
                errors.put("video", List.of(MessageUtils.getMessage(Constants.VIDEO_SIZE_INVALID)));
            }
        }
        if (imageSize > 1024 * 1024 * 30) {
            errors.put("file", List.of(MessageUtils.getMessage(Constants.UPLOAD_FILE_FAILED)));
        }
    }

    private void validateUpdatePostArticle(UpdatePostArticleRequest updatePostArticleRequest) {
        requestValidator.validateRequest(updatePostArticleRequest);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private boolean isImageFile(String fileExtension) {
        List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "svg", "webp", "tiff");
        return validExtensions.contains(fileExtension);
    }

    private boolean isVideoFile(String fileExtension) {
        List<String> validExtensions = Arrays.asList("mp4", "mov", "avi", "flv", "wmv", "webm");
        return validExtensions.contains(fileExtension);
    }
}