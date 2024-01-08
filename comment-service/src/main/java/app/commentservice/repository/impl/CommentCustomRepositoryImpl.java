package app.commentservice.repository.impl;

import app.commentservice.dto.request.ParentCommentRequest;
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
    public List<ParentComment> findParentCommentByPostId(long postId) {
        TypedQuery query = entityManager.createQuery("SELECT p FROM ParentComment p WHERE p.postId = :id ORDER BY p.createdAt ASC", ParentComment.class);
        query.setParameter("id", postId);
        return query.getResultList();
    }

    @Override
    public ParentComment addParentComment(ParentComment parentComment) {
        entityManager.persist(parentComment);
        return parentComment;
    }


}
