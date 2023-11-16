package defence.app.repository;

import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  Optional<CategoryEntity> findByName(CategoryEnum categoryEnum);

  Optional<CategoryEntity> findById(Long id);
}
