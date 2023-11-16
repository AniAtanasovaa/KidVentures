package defence.app.repository;

import defence.app.model.entity.RoleEntity;
import defence.app.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
   Optional<RoleEntity> findByRole(RoleEnum role);

}
