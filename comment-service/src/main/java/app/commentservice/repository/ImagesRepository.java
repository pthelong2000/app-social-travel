package app.commentservice.repository;

import app.commentservice.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Long, Images>, ImagesCustomRepository {
}
