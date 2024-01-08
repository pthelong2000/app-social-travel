package app.commentservice.repository;

import app.commentservice.entity.Images;

import java.util.Optional;

public interface ImagesCustomRepository {
    Optional<Images> findImagesById(long id);
}
