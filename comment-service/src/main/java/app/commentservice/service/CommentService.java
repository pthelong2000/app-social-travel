package app.commentservice.service;

import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ParentCommentResponse;

import java.util.List;

public interface CommentService{

    ParentCommentResponse addParentComment(ParentCommentRequest parentCommentRequest);
    List<ParentCommentResponse> getListParentCommentByPostId(long postId);
    ParentCommentResponse updateParentComment(Long id, String content);
    void deleteParentComment(Long id);
}
