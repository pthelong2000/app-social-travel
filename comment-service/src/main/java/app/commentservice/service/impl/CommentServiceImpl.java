package app.commentservice.service.impl;

import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.entity.ParentComment;
import app.commentservice.repository.ChildCommentRepository;
import app.commentservice.repository.ImagesRepository;
import app.commentservice.repository.ParentCommentRepository;
import app.commentservice.service.CommentService;
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

    @Override
    public ParentCommentResponse addParentComment(ParentCommentRequest parentCommentRequest) {
        ParentComment parentComment = ParentComment.builder()
                .postId(parentCommentRequest.getPostId())
                .userId(parentCommentRequest.getUserId())
                .content(parentCommentRequest.getContent())
                .imageId(parentCommentRequest.getImageId())
                .build();
        parentComment = parentCommentRepository.addParentComment(parentComment);
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
}
