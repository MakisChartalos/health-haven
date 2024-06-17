package gr.aueb.cf.medicalappointmentmanager.validator;

import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates {@link PatientUpdateDTO} instances to ensure that the provided data meets the expected
 * format and business rules before processing by the application.
 *
 * This validator checks for the correctness of first names, last names, email addresses,
 * and phone numbers to prevent invalid data from being entered into the system.
 */
@Component
public class PatientUpdateValidator implements Validator {

    /**
     * Determines if this validator can validate instances of the given class.
     *
     * @param clazz the class to check
     * @return true if this validator supports the given class; false otherwise
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return PatientUpdateDTO.class.equals(clazz);
    }

    /**
     * Validates the provided {@link PatientUpdateDTO} instance.
     *
     * @param target the target object to validate
     * @param errors the errors object to register validation errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        PatientUpdateDTO patientToUpdate = (PatientUpdateDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty", "First name cannot be empty.");
        if (patientToUpdate.getFirstname().length() < 2 || patientToUpdate.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "size", "First name must be between 2 and 32 characters.");
        }
        if (!patientToUpdate.getFirstname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("firstname", "containsIntegersOrSpecialChars", "First name must contain only letters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty", "Last name cannot be empty.");
        if (patientToUpdate.getLastname().length() < 2 || patientToUpdate.getLastname().length() > 32) {
            errors.rejectValue("lastname", "size", "Last name must be between 2 and 32 characters.");
        }
        if (!patientToUpdate.getLastname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("lastname", "containsIntegersOrSpecialChars", "Last name must contain only letters.");
        }

        if (!patientToUpdate.getEmail().matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            errors.rejectValue("email", "invalidEmail", "Invalid email format.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "phoneNumber.empty", "Phone number cannot be empty.");
        if (!patientToUpdate.getPhoneNumber().matches("^\\d{10}$")) {
            errors.rejectValue("phoneNumber", "phoneNumber.invalid", "Phone number must be exactly 10 digits.");
        }
    }
}
