package gr.aueb.cf.medicalappointmentmanager.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Provides static methods for validating usernames and passwords to ensure they meet
 * specific format and security requirements before being processed by the application.
 */
public class UsernameAndPasswordValidator  {

    /**
     * Validates the provided username and password.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @param errors   the errors object to register validation errors
     */
    public static void validateUsernameAndPassword(String username, String password, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (username.length() < 5 || username.length() > 20) {
            errors.rejectValue("username", "username.invalid", "Username must be between 5 and 20 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (password.length() < 8 || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            errors.rejectValue("password", "password.invalid", "Password must be at least 8 characters and include a number, uppercase, lowercase, and special character.");
        }
    }

}
