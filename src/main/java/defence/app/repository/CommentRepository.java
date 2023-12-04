package defence.app.repository;

import defence.app.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByPlaceId(Long placeId);

    void deleteAllByPlaceId(Long id);
}
