package gr.aueb.cf.healthhaven.service.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private final static Long serialVersionUID = 1L;

    public UserAlreadyExistsException(String username) {
        super("User with username: " + username + " already exists");
    }
}
