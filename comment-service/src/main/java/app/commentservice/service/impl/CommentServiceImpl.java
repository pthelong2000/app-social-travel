package app.commentservice.service.impl;

import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.entity.ParentComment;
import app.commentservice.repository.ChildCommentRepository;
import app.commentservice.repository.ImagesRepository;
import app.commentservice.repository.ParentCommentRepository;
import app.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public long addParentComment(ParentCommentRequest parentCommentRequest) {
        ParentComment parentComment = ParentComment.builder()
                .postId(parentCommentRequest.getPostId())
                .userId(parentCommentRequest.getUserId())
                .content(parentCommentRequest.getContent())
                .imageId(parentCommentRequest.getImageId())
                .build();
        parentComment = parentCommentRepository.addParentComment(parentComment);
        return parentComment.getId();
    }

    @Override
    public List<ParentCommentResponse> getListParentCommentByPostId(long postId) {
        List<ParentComment> parentCommentList = parentCommentRepository.findParentCommentByPostId(postId);
        if (parentCommentList.isEmpty()) {
            return List.of();
        }
        List<ParentCommentResponse> parentCommentResponseList = parentCommentList.stream()
                .map(parentComment -> ParentCommentResponse.builder()
                        .postId(String.valueOf(parentComment.getPostId()))
                        .userId(String.valueOf(parentComment.getUserId()))
                        .content(parentComment.getContent())
                        .image(commentImagesRepository.findImagesById(parentComment.getImageId()).orElse(null).getUrl())
                        .createdBy(parentComment.getCreatedBy())
                        .createdAt(convertDateTimeToString(parentComment.getCreatedAt()))
                        .updatedBy(parentComment.getLastModifiedBy())
                        .updatedAt(convertDateTimeToString(parentComment.getLastModifiedAt()))
                        .build())
                .toList();
        return parentCommentResponseList;
    }

    private String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Chuyển đổi LocalDateTime thành String
        String formattedDateTime = dateTime.format(formatter);

        return formattedDateTime;
    }
}
