package gr.aueb.cf.healthhaven.repository;

import gr.aueb.cf.healthhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

}
