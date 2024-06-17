package gr.aueb.cf.medicalappointmentmanager.repository;

import gr.aueb.cf.medicalappointmentmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

}
