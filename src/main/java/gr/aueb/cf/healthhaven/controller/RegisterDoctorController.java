package gr.aueb.cf.healthhaven.controller;

import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorRegisterDTO;
import gr.aueb.cf.healthhaven.model.Doctor;
import gr.aueb.cf.healthhaven.service.DoctorServiceImpl;
import gr.aueb.cf.healthhaven.service.UserServiceImpl;
import gr.aueb.cf.healthhaven.service.exceptions.DoctorAlreadyExistsException;
import gr.aueb.cf.healthhaven.service.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.healthhaven.validator.DoctorRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling doctor registration requests.
 */
@Controller
public class RegisterDoctorController {

    private final UserServiceImpl userService;
    private final DoctorServiceImpl doctorService;
    private final DoctorRegisterValidator validator;

    /**
     * Constructs a RegisterDoctorController with the specified services and validator.
     *
     * @param userService   the user service
     * @param doctorService the doctor service
     * @param validator     the doctor register validator
     */
    @Autowired
    public RegisterDoctorController(UserServiceImpl userService, DoctorServiceImpl doctorService, DoctorRegisterValidator validator) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.validator = validator;
    }

    /**
     * Displays the doctor registration form.
     *
     * @param model the model
     * @return the view name for the doctor registration form
     */
    @GetMapping("/doctors/register")
    public String register(Model model) {
        model.addAttribute("doctorRegisterDTO", new DoctorRegisterDTO());
        return "register-doctor";
    }

    /**
     * Handles the doctor registration process.
     *
     * @param dto          the doctor register DTO
     * @param bindingResult the binding result for validation
     * @param model        the model
     * @return the view name after registering the doctor
     * @throws DoctorAlreadyExistsException if the doctor already exists
     * @throws UserAlreadyExistsException   if the user already exists
     */
    @PostMapping("/doctors/register")
    public String registerDoctor(@ModelAttribute("doctorRegisterDTO") DoctorRegisterDTO dto, BindingResult bindingResult, Model model) throws DoctorAlreadyExistsException, UserAlreadyExistsException {
        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register-doctor";
        }
        try {
            Doctor registerDoctor = doctorService.registerDoctor(dto);
            model.addAttribute("successMessage", "Registration successful!");
        } catch (DoctorAlreadyExistsException | UserAlreadyExistsException e) {
            throw e;
        }
        return "register-doctor";
    }
}
