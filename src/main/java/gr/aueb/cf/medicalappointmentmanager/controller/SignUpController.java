package gr.aueb.cf.medicalappointmentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling sign-up requests.
 */
@Controller
public class SignUpController {

    /**
     * Displays the sign-up page.
     *
     * @return the view name for the sign-up page
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
