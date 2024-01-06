package app.commentservice.repository;

import app.commentservice.dto.response.ChildCommentResponse;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.entity.ChildComment;
import app.commentservice.entity.ParentComment;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    Optional<ParentComment> findParentCommentById(long id);
    List<ChildComment> findChildCommentById(long id);
}
