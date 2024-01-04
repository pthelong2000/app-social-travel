package app.commentservice.repository;

import app.commentservice.entity.ParentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentCommentRepository extends JpaRepository<Long, ParentComment> {
}
