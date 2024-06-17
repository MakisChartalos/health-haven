package gr.aueb.cf.medicalappointmentmanager.controller;

import gr.aueb.cf.medicalappointmentmanager.dto.appointmentDTO.AppointmentRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.service.IAppointmentService;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.InvalidAppointmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling appointment-related requests for patients.
 */
@Controller
@RequestMapping("patients/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    /**
     * Constructs an AppointmentController with the specified appointment service.
     *
     * @param appointmentService the appointment service
     */
    @Autowired
    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Displays the form for booking an appointment.
     *
     * @param doctorId the ID of the doctor
     * @param model    the model
     * @return the view name for booking an appointment
     */
    @GetMapping("/book")
    public String showBookAppointmentForm(@RequestParam("doctorId") Long doctorId, Model model) {
        AppointmentRegisterDTO appointmentForm = new AppointmentRegisterDTO();
        model.addAttribute("appointmentForm", appointmentForm);
        model.addAttribute("doctorId", doctorId);
        return "book-appointment";
    }

    /**
     * Handles the booking of an appointment.
     *
     * @param appointmentForm the appointment registration form
     * @param model           the model
     * @return the view name after booking an appointment
     * @throws InvalidAppointmentException if the appointment is invalid
     * @throws EntityNotFoundException     if the entity is not found
     */
    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute("appointmentForm") AppointmentRegisterDTO appointmentForm, Model model) throws InvalidAppointmentException, EntityNotFoundException {
        try {
            appointmentService.createAppointment(appointmentForm);
            model.addAttribute("successMessage", true);
        } catch (InvalidAppointmentException | EntityNotFoundException e) {
            throw e;
        }
        return "book-appointment";
    }
}
