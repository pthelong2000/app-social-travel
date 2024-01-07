package app.commentservice.repository.impl;

import app.commentservice.entity.Images;
import app.commentservice.repository.ImagesCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImagesRepositoryImpl implements ImagesCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    @Override
    public Optional<Images> findImagesById(long id) {
        TypedQuery query = entityManager.createQuery("SELECT i FROM Images i WHERE i.id = :id", Images.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst();
    }
}
