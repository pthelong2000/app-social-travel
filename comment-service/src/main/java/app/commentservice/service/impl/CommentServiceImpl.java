package app.commentservice.service.impl;

import app.commentservice.dto.request.ChildCommentRequest;
import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ChildCommentResponse;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.entity.ChildComment;
import app.commentservice.entity.Images;
import app.commentservice.entity.ParentComment;
import app.commentservice.repository.ChildCommentRepository;
import app.commentservice.repository.ImagesRepository;
import app.commentservice.repository.ParentCommentRepository;
import app.commentservice.service.CommentService;
import app.commentservice.service.external.FileService;
import app.commentservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ParentCommentRepository parentCommentRepository;
    private final ChildCommentRepository childCommentRepository;
    private final ImagesRepository commentImagesRepository;
    private final FileService fileService;

    @Override
    public ParentCommentResponse addParentComment(ParentCommentRequest parentCommentRequest) {

        if (checkContentAndImageIsNull(parentCommentRequest.getContent(), parentCommentRequest.getImage())){
            throw new RuntimeException(Constants.CONTENT_AND_IMAGE_IS_NULL);
        }

        Images images = null;
        if (Objects.nonNull(parentCommentRequest.getImage())) {
            images = Images.builder()
                    .url(fileService.uploadFile(parentCommentRequest.getImage()))
                    .build();
        }


        ParentComment parentComment = ParentComment.builder()
                .postId(parentCommentRequest.getPostId())
                .userId(parentCommentRequest.getUserId())
                .content(parentCommentRequest.getContent())
                .imageId(Objects.nonNull(parentCommentRequest.getImage()) ? images.getId() : 0)
                .build();
        parentComment = parentCommentRepository.save(parentComment);
        return convertToParentCommentResponse(parentComment);
    }

    @Override
    public List<ParentCommentResponse> getListParentCommentByPostId(long postId) {
        List<ParentComment> parentCommentList = parentCommentRepository.findParentCommentByPostId(postId);
        if (parentCommentList.isEmpty()) {
            return List.of();
        }
        return parentCommentList.stream()
                .map(this::convertToParentCommentResponse)
                .toList();
    }

    @Override
    public ParentCommentResponse updateParentComment(Long id, String content) {

        ParentComment parentComment = parentCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(Constants.PARENT_COMMENT_NOT_FOUND)
        );
        if (StringUtils.isNotBlank(content)) {
            parentCommentRepository.updateParentComment(id, content);
        }

        return convertToParentCommentResponse(parentComment);
    }

    @Override
    public void deleteParentComment(Long id) {
        parentCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(Constants.PARENT_COMMENT_NOT_FOUND)
        );

        parentCommentRepository.deleteById(id);
    }

    @Override
    public ChildCommentResponse addChildComment(ChildCommentRequest childCommentRequest) {
        if (checkContentAndImageIsNull(childCommentRequest.getContent(), childCommentRequest.getImage())) {
            throw new RuntimeException(Constants.CONTENT_AND_IMAGE_IS_NULL);
        }

        Images images = null;
        if (Objects.nonNull(childCommentRequest.getImage())) {
            images = Images.builder()
                    .url(fileService.uploadFile(childCommentRequest.getImage()))
                    .build();
        }
        ChildComment childComment = ChildComment.builder()
                .parentCommentId(childCommentRequest.getParentCommentId())
                .userId(childCommentRequest.getUserId())
                .parentCommentId(childCommentRequest.getParentCommentId())
                .content(childCommentRequest.getContent())
                .imageId(Objects.nonNull(childCommentRequest.getImage()) ? images.getId() : 0)
                .build();
        return convertToChildCommentResponse(childCommentRepository.save(childComment));
    }

    @Override
    public ChildCommentResponse updateChildComment(Long id, String content) {
        ChildComment childComment = childCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(Constants.CHILD_COMMENT_NOT_FOUND)
        );
        if (StringUtils.isNotBlank(content)) {
            childCommentRepository.updateChildComment(id, content);
        }
        return convertToChildCommentResponse(childComment);
    }

    @Override
    public void deleteChildComment(Long id) {
        childCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(Constants.CHILD_COMMENT_NOT_FOUND)
        );
        childCommentRepository.deleteById(id);
    }

    private String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return dateTime.format(formatter);
    }

    private ParentCommentResponse convertToParentCommentResponse(ParentComment parentComment) {
        Images images = commentImagesRepository.findImagesById(parentComment.getImageId());
        return ParentCommentResponse.builder()
                .postId(String.valueOf(parentComment.getPostId()))
                .userId(String.valueOf(parentComment.getUserId()))
                .content(parentComment.getContent())
                .image(Objects.nonNull(images) ? images.getUrl() : null)
                .createdBy(parentComment.getCreatedBy())
                .createdAt(convertDateTimeToString(parentComment.getCreatedAt()))
                .updatedBy(parentComment.getLastModifiedBy())
                .updatedAt(convertDateTimeToString(parentComment.getLastModifiedAt()))
                .build();
    }

    private ChildCommentResponse convertToChildCommentResponse(ChildComment childComment) {
        Images images = commentImagesRepository.findImagesById(childComment.getImageId());
        return ChildCommentResponse.builder()
                .parentId(String.valueOf(childComment.getParentCommentId()))
                .userId(String.valueOf(childComment.getUserId()))
                .content(childComment.getContent())
                .image(Objects.nonNull(images) ? images.getUrl() : null)
                .createdBy(childComment.getCreatedBy())
                .createdAt(convertDateTimeToString(childComment.getCreatedAt()))
                .updatedBy(childComment.getLastModifiedBy())
                .updatedAt(convertDateTimeToString(childComment.getLastModifiedAt()))
                .build();
    }

    private boolean checkContentAndImageIsNull(String content, MultipartFile images) {
        return StringUtils.isEmpty(content) && Objects.isNull(images);
    }
}
