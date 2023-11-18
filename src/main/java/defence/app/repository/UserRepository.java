package defence.app.repository;

import defence.app.model.entity.UserEntity;

import defence.app.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
    Optional<UserEntity> findByUsername(String username);

    UserEntity findFirstByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u JOIN u.roles r WHERE r.role = 'ADMIN'")
    boolean existsByRoles_Role(RoleEnum role);

    Optional<UserEntity> findByEmail(String email);

    long countByRegistrationDate(LocalDate date);

}
