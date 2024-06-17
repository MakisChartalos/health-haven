package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.model.User;
import gr.aueb.cf.medicalappointmentmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users within the Medical Appointment Management system.
 * This class handles all business logic related to user entities, including retrieval
 * of user details based on the username.
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve.
     * @return the user with the given username.
     * @throws UsernameNotFoundException if no user is found with the provided username.
     */
    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        try {
            user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
            log.info("User with username: " + username + " was found");
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }
}
