package app.commentservice.service;

import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ParentCommentResponse;

import java.util.List;

public interface CommentService{

    long addParentComment(ParentCommentRequest parentCommentRequest);
    List<ParentCommentResponse> getListParentCommentByPostId(long postId);
}
