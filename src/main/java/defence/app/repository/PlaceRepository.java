package defence.app.repository;

import defence.app.model.entity.PlaceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {


void deleteById(Long id);
}
