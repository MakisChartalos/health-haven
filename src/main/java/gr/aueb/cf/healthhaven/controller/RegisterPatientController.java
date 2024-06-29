package gr.aueb.cf.healthhaven.controller;

import gr.aueb.cf.healthhaven.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.healthhaven.model.Patient;
import gr.aueb.cf.healthhaven.service.IPatientService;
import gr.aueb.cf.healthhaven.service.IUserService;
import gr.aueb.cf.healthhaven.service.exceptions.PatientAlreadyExistsException;
import gr.aueb.cf.healthhaven.service.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.healthhaven.validator.PatientRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling patient registration requests.
 */
@Controller
public class RegisterPatientController {

    private final IPatientService patientService;
    private final IUserService userService;
    private final PatientRegisterValidator validator;

    /**
     * Constructs a RegisterPatientController with the specified services and validator.
     *
     * @param patientService the patient service
     * @param userService    the user service
     * @param validator      the patient register validator
     */
    @Autowired
    public RegisterPatientController(IPatientService patientService, IUserService userService, PatientRegisterValidator validator) {
        this.patientService = patientService;
        this.userService = userService;
        this.validator = validator;
    }

    /**
     * Displays the patient registration form.
     *
     * @param model the model
     * @return the view name for the patient registration form
     */
    @GetMapping("/patients/register")
    public String register(Model model) {
        model.addAttribute("patientRegisterDTO", new PatientRegisterDTO());
        return "register-patient";
    }

    /**
     * Handles the patient registration process.
     *
     * @param dto           the patient register DTO
     * @param bindingResult the binding result for validation
     * @param model         the model
     * @return the view name after registering the patient
     * @throws PatientAlreadyExistsException if the patient already exists
     * @throws UserAlreadyExistsException    if the user already exists
     */
    @PostMapping("/patients/register")
    public String registerPatient(@ModelAttribute("patientRegisterDTO") PatientRegisterDTO dto, BindingResult bindingResult, Model model) throws PatientAlreadyExistsException, UserAlreadyExistsException {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register-patient";
        }
        try {
            Patient registerPatient = patientService.registerPatient(dto);
            model.addAttribute("successMessage", "Registration successful!");
        } catch (PatientAlreadyExistsException | UserAlreadyExistsException e) {
            throw e;
        }
        return "register-patient";
    }
}
