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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ParentCommentRepository parentCommentRepository;
    private final ChildCommentRepository childCommentRepository;
    private final ImagesRepository commentImagesRepository;
    private final FileService fileService;

    @Override
    public ParentCommentResponse addParentComment(ParentCommentRequest parentCommentRequest) {

        Images images = Images.builder()
                .url(fileService.uploadFile(parentCommentRequest.getImage()))
                .build();

        ParentComment parentComment = ParentComment.builder()
                .postId(parentCommentRequest.getPostId())
                .userId(parentCommentRequest.getUserId())
                .content(parentCommentRequest.getContent())
                .imageId(images.getId())
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
                () -> new RuntimeException("Parent comment not found")
        );
        if (StringUtils.isNotBlank(content)) {
            parentCommentRepository.updateParentComment(id, content);
        }

        return convertToParentCommentResponse(parentComment);
    }

    @Override
    public void deleteParentComment(Long id) {
        parentCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Parent comment not found")
        );

        parentCommentRepository.deleteById(id);
    }

    @Override
    public ChildCommentResponse addChildComment(ChildCommentRequest childCommentRequest) {
        Images images = Images.builder()
                .url(fileService.uploadFile(childCommentRequest.getImage()))
                .build();
        ChildComment childComment = ChildComment.builder()
                .parentCommentId(childCommentRequest.getParentCommentId())
                .userId(childCommentRequest.getUserId())
                .parentCommentId(childCommentRequest.getParentCommentId())
                .content(childCommentRequest.getContent())
                .imageId(images.getId())
                .build();
        return convertToChildCommentResponse(childCommentRepository.save(childComment));
    }

    @Override
    public ChildCommentResponse updateChildComment(Long id, String content) {
        ChildComment childComment = childCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Child comment not found")
        );
        if (StringUtils.isNotBlank(content)) {
            childCommentRepository.updateChildComment(id, content);
        }
        return convertToChildCommentResponse(childComment);
    }

    @Override
    public void deleteChildComment(Long id) {
        childCommentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Child comment not found")
        );
        childCommentRepository.deleteById(id);
    }

    private String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return dateTime.format(formatter);
    }

    private ParentCommentResponse convertToParentCommentResponse(ParentComment parentComment) {
        return ParentCommentResponse.builder()
                .postId(String.valueOf(parentComment.getPostId()))
                .userId(String.valueOf(parentComment.getUserId()))
                .content(parentComment.getContent())
                .image(commentImagesRepository.findImagesById(parentComment.getImageId()).orElse(null).getUrl())
                .createdBy(parentComment.getCreatedBy())
                .createdAt(convertDateTimeToString(parentComment.getCreatedAt()))
                .updatedBy(parentComment.getLastModifiedBy())
                .updatedAt(convertDateTimeToString(parentComment.getLastModifiedAt()))
                .build();
    }

    private ChildCommentResponse convertToChildCommentResponse(ChildComment childComment) {
        return ChildCommentResponse.builder()
                .parentId(String.valueOf(childComment.getParentCommentId()))
                .userId(String.valueOf(childComment.getUserId()))
                .content(childComment.getContent())
                .image(commentImagesRepository.findImagesById(childComment.getImageId()).orElse(null).getUrl())
                .createdBy(childComment.getCreatedBy())
                .createdAt(convertDateTimeToString(childComment.getCreatedAt()))
                .updatedBy(childComment.getLastModifiedBy())
                .updatedAt(convertDateTimeToString(childComment.getLastModifiedAt()))
                .build();
    }
}
