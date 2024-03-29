package app.postservice.repository.impl;

import app.postservice.repository.PostCustomRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCustomIpmlRepository implements PostCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public void findByName(String name) {
        TypedQuery<String> query = entityManager.createQuery("SELECT p.title FROM PostArticle p WHERE p.title = :name", String.class);
        query.setParameter("name", name);
        List<String> resultList = query.getResultList();
    }
}
