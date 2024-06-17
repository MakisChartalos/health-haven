package gr.aueb.cf.medicalappointmentmanager.controller;

import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.model.Appointment;
import gr.aueb.cf.medicalappointmentmanager.model.AppointmentStatus;
import gr.aueb.cf.medicalappointmentmanager.model.Doctor;
import gr.aueb.cf.medicalappointmentmanager.model.User;
import gr.aueb.cf.medicalappointmentmanager.service.IAppointmentService;
import gr.aueb.cf.medicalappointmentmanager.service.IDoctorService;
import gr.aueb.cf.medicalappointmentmanager.service.IUserService;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller for handling doctor-related requests and operations.
 */
@Controller
@RequestMapping("/doctors")
public class DoctorDashboardController {

    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;
    private final IUserService userService;

    /**
     * Constructs a DoctorDashboardController with the specified services.
     *
     * @param doctorService      the doctor service
     * @param appointmentService the appointment service
     * @param userService        the user service
     */
    @Autowired
    public DoctorDashboardController(IDoctorService doctorService, IAppointmentService appointmentService, IUserService userService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    /**
     * Displays the doctor dashboard.
     *
     * @param model     the model
     * @param principal the principal
     * @return the view name for the doctor dashboard
     * @throws EntityNotFoundException if the entity is not found
     */
    @GetMapping("/dashboard")
    public String doctorDashboard(Model model, Principal principal) throws EntityNotFoundException {
        try {
            String username = principal.getName();
            User user = userService.getUserByUsername(username);
            Doctor doctor = user.getDoctor();
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctor.getId());
            model.addAttribute("appointments", appointments);
            model.addAttribute("noAppointments", false);
        } catch (EntityNotFoundException e) {
            model.addAttribute("noAppointments", true);
        }
        return "doctors-dashboard";
    }

    /**
     * Changes the status of an appointment.
     *
     * @param id     the ID of the appointment
     * @param status the new status of the appointment
     * @param model  the model
     * @return the view name after changing the status of the appointment
     * @throws EntityNotFoundException if the entity is not found
     */
    @PostMapping("/appointments/changeStatus")
    public String changeAppointmentStatus(@RequestParam("id") Long id, @RequestParam("status") String status, Model model) throws EntityNotFoundException {
        try {
            AppointmentStatus newStatus = AppointmentStatus.valueOf(status);
            appointmentService.updateAppointmentStatus(id, newStatus);
            return "redirect:/doctors/dashboard";
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    /**
     * Displays the form for updating the doctor's profile.
     *
     * @param model the model
     * @return the view name for updating the doctor's profile
     */
    @GetMapping("/profile/update")
    public String updateProfileForm(Model model) {
        DoctorUpdateDTO doctorUpdateDTO = new DoctorUpdateDTO();
        model.addAttribute("doctorUpdateDTO", doctorUpdateDTO);
        return "update-doctor-profile";
    }

    /**
     * Handles the profile update for a doctor.
     *
     * @param doctorUpdateDTO the doctor update DTO
     * @param model           the model
     * @return the view name after updating the doctor's profile
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("doctorUpdateDTO") DoctorUpdateDTO doctorUpdateDTO, Model model) {
        try {
            doctorService.updateDoctor(doctorUpdateDTO);
            model.addAttribute("successMessage", "Profile updated successfully!");
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "An error occurred while updating the profile: " + e.getMessage());
        }
        return "update-doctor-profile";
    }
}
