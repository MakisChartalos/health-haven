package gr.aueb.cf.healthhaven.validator;


import gr.aueb.cf.healthhaven.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.healthhaven.dto.patientDTO.PatientUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates {@link PatientRegisterDTO} instances to ensure that the provided data meets the expected
 * format and business rules before processing by the application.
 *
 * This validator checks for the correctness of usernames, passwords, first names, last names,
 * SSN, email, and phone numbers to prevent invalid data from being entered into the system.
 */
@Component
public class PatientRegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PatientUpdateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PatientRegisterDTO newPatient = (PatientRegisterDTO) target;

        UsernameAndPasswordValidator.validateUsernameAndPassword(newPatient.getUsername(), newPatient.getPassword(), errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty", "First name cannot be empty.");
        if (newPatient.getFirstname().length() < 2 || newPatient.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "size", "First name must be between 2 and 32 characters.");
        }
        if (!newPatient.getFirstname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("firstname", "containsIntegersOrSpecialChars", "First name must contain only letters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty", "Last name cannot be empty.");
        if (newPatient.getLastname().length() < 2 || newPatient.getLastname().length() > 32) {
            errors.rejectValue("lastname", "size", "Last name must be between 2 and 32 characters.");
        }
        if (!newPatient.getLastname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("lastname", "containsIntegersOrSpecialChars", "Last name must contain only letters.");
        }

        if (!newPatient.getEmail().matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            errors.rejectValue("email", "invalidEmail", "Invalid email format.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "phoneNumber.empty", "Phone number cannot be empty.");
        if (!newPatient.getPhoneNumber().matches("^\\d{10}$")) {
            errors.rejectValue("phoneNumber", "phoneNumber.invalid", "Phone number must be exactly 10 digits.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "ssn.empty", "SSN cannot be empty.");
        if (!newPatient.getSsn().matches("^\\d{9}$")) {
            errors.rejectValue("ssn", "ssn.invalid", "SSN must be exactly 9 digits.");
        }


    }
}
