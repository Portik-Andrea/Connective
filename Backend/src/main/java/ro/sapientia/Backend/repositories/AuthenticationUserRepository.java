package ro.sapientia.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.Backend.domains.AuthenticationUser;

public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser, Long> {
    AuthenticationUser findByEmail(String email);
}
