package security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
