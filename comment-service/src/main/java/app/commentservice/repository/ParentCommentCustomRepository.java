package app.commentservice.repository;

import app.commentservice.entity.ParentComment;

import java.util.List;

public interface ParentCommentCustomRepository {
    List<ParentComment> findParentCommentByPostId(long postId);
    void updateParentComment(long id, String content);
}
