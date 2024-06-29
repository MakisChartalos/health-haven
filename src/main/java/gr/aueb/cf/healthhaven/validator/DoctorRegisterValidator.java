package gr.aueb.cf.healthhaven.validator;

import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorRegisterDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates {@link DoctorRegisterDTO} instances to ensure that the provided data meets the expected
 * format and business rules before processing by the application.
 *
 * This validator checks for the correctness of usernames, passwords, first names, last names,
 * specialties, and medical license numbers to prevent invalid data from being entered into the system.
 */
@Component
public class DoctorRegisterValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return DoctorRegisterDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorRegisterDTO newDoctor = (DoctorRegisterDTO) target;

        UsernameAndPasswordValidator.validateUsernameAndPassword(newDoctor.getUsername(), newDoctor.getPassword(), errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","empty");
        if (newDoctor.getFirstname().length() < 2 || newDoctor.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "size");
        }
        if (!newDoctor.getFirstname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("firstname", "containsIntegersOrSpecialChars");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (newDoctor.getLastname().length() < 2 || newDoctor.getLastname().length() > 32) {
            errors.rejectValue("lastname", "size");
        }
        if (!newDoctor.getLastname().matches("^[A-Za-z]+$")) {
            errors.rejectValue("lastname", "containsIntegersOrSpecialChars");
        }

        if (newDoctor.getSpeciality() == null) {
            errors.rejectValue("speciality", "speciality.null", "Specialty is required.");
        }

        if (!newDoctor.getMedicalLicenseNumber().matches("\\d{7}")) {
            errors.rejectValue("medicalLicenseNumber", "license.format", "Medical License Number must be 7 digits.");
        }

    }

}
