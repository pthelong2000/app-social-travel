package app.commentservice.repository;

import app.commentservice.entity.ChildComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCommentRepository extends JpaRepository<ChildComment, Long>, ChildCommentCustomRepository {
}
