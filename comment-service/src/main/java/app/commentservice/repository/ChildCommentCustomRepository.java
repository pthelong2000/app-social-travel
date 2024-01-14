package app.commentservice.repository;

public interface ChildCommentCustomRepository {
    void updateChildComment(long id, String content);
}
