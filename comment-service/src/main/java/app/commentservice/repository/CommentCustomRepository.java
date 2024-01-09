package app.commentservice.repository;

import app.commentservice.entity.ParentComment;

import java.util.List;

public interface CommentCustomRepository {
    List<ParentComment> findParentCommentByPostId(long postId);
    ParentComment addParentComment(ParentComment parentComment);
    void updateParentComment(long id, String content);
}
