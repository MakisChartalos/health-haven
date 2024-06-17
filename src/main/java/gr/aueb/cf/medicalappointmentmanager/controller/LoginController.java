package gr.aueb.cf.medicalappointmentmanager.controller;

import gr.aueb.cf.medicalappointmentmanager.model.User;
import gr.aueb.cf.medicalappointmentmanager.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Controller for handling login requests.
 */
@Controller
public class LoginController {

    private final IUserService userService;

    /**
     * Constructs a LoginController with the specified user service.
     *
     * @param userService the user service
     */
    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the login page.
     *
     * @param model     the model
     * @param principal the principal
     * @param request   the HTTP request
     * @return the view name for the login page
     */
    @GetMapping({"/", "/login"})
    String login(Model model, Principal principal, HttpServletRequest request) {
        String error = request.getParameter("error");
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        if (principal == null) return "login";

        User user = userService.getUserByUsername(principal.getName());
        String role = user.getRole().name();

        if (role.equals("DOCTOR")) {
            return "redirect:/doctors/dashboard";
        } else {
            return "redirect:/patients/dashboard";
        }
    }
}
