package app.commentservice.repository.impl;

import app.commentservice.dto.response.ChildCommentResponse;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.entity.ChildComment;
import app.commentservice.entity.ParentComment;
import app.commentservice.repository.CommentCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    @Override
    @Transactional(readOnly = true)
    public Optional<ParentComment> findParentCommentById(long id) {
        TypedQuery query = entityManager.createQuery("SELECT p FROM ParentComment p WHERE p.id = :id", ParentComment.class);
        query.setParameter("id", id);
        return Optional.empty();
    }

    @Override
    public List<ChildComment> findChildCommentById(long id) {
        TypedQuery query = entityManager.createQuery("SELECT c FROM ChildComment c WHERE c.id = :id", ChildComment.class);
        query.setParameter("id", id);
        List<ChildComment> childComments = query.getResultList();
        return childComments;
    }
}
