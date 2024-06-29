package gr.aueb.cf.healthhaven.validator;

import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates {@link DoctorUpdateDTO} instances to ensure that the provided data meets the expected
 * format and business rules before processing by the application.
 *
 * This validator checks for the correctness of first names, last names, and specialties
 * to prevent invalid data from being entered into the system.
 */
@Component
public class DoctorUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DoctorUpdateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorUpdateDTO doctorToUpdate = (DoctorUpdateDTO) target;


        // Validate firstname
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "firstname.empty", "First name cannot be empty.");
        if (doctorToUpdate.getFirstname() != null && (doctorToUpdate.getFirstname().length() < 2 || doctorToUpdate.getFirstname().length() > 32)) {
            errors.rejectValue("firstname", "firstname.length", "First name must be between 2 and 32 characters.");
        }

        //Validate lastname
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "lastname.empty", "Last name cannot be empty.");
        if (doctorToUpdate.getLastname() != null && (doctorToUpdate.getLastname().length() < 2 || doctorToUpdate.getLastname().length() > 32)) {
            errors.rejectValue("lastname", "lastname.length", "Last name must be between 2 and 32 characters.");
        }

        // Validate specialty
        if (doctorToUpdate.getSpeciality() == null) {
            errors.rejectValue("specialty", "specialty.null", "Specialty is required.");
        }


    }
}
