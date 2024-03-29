package ro.sapientia.Backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.domains.UserType;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findAll();

    Optional<UserEntity> findByToken(String token);

    List<UserEntity> findAllByType(UserType type);

    List<UserEntity> findByMentorIsNotNull();
}
