package app.commentservice.repository.impl;

import app.commentservice.entity.ParentComment;
import app.commentservice.repository.ParentCommentCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParentCommentCustomRepositoryImpl implements ParentCommentCustomRepository {

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
    @Transactional(readOnly = true)
    public void updateParentComment(long id, String content) {
        entityManager.createNativeQuery("UPDATE parent_comment SET content = ? WHERE id = ?")
                .setParameter(1, content)
                .setParameter(2, id)
                .executeUpdate();
    }


}
