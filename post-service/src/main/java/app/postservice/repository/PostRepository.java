package app.postservice.repository;



import app.postservice.entity.PostArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostArticle, Long> {

}
