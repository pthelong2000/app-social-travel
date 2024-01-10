package app.commentservice.repository.impl;

import app.commentservice.repository.ChildCommentCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ChildCommentCustomRepositoryImpl implements ChildCommentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void updateChildComment(long id, String content) {
        entityManager.createNativeQuery("UPDATE child_comment SET content = ? WHERE id = ?")
                .setParameter(1, content)
                .setParameter(2, id)
                .executeUpdate();
    }
}
