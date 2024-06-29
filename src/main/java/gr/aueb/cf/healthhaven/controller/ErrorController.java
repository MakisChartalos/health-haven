package gr.aueb.cf.healthhaven.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice for handling exceptions globally.
 */
@ControllerAdvice
public class ErrorController {

    /**
     * Handles runtime exceptions.
     *
     * @param model the model
     * @param ex    the runtime exception
     * @return the view name for the error page
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public String handleBadRequest(Model model, RuntimeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    /**
     * Handles general exceptions.
     *
     * @param model the model
     * @param e     the exception
     * @return the view name for the error page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
