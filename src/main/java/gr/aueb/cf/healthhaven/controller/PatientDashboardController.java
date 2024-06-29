package gr.aueb.cf.healthhaven.controller;

import gr.aueb.cf.healthhaven.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.healthhaven.model.Appointment;
import gr.aueb.cf.healthhaven.model.Speciality;
import gr.aueb.cf.healthhaven.service.IAppointmentService;
import gr.aueb.cf.healthhaven.service.IDoctorService;
import gr.aueb.cf.healthhaven.service.IPatientService;
import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling patient dashboard requests and operations.
 */
@Controller
@RequestMapping("patients")
public class PatientDashboardController {

    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;
    private final IPatientService patientService;

    /**
     * Constructs a PatientDashboardController with the specified services.
     *
     * @param doctorService      the doctor service
     * @param appointmentService the appointment service
     * @param patientService     the patient service
     */
    @Autowired
    public PatientDashboardController(IDoctorService doctorService, IAppointmentService appointmentService, IPatientService patientService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    /**
     * Displays the patient dashboard.
     *
     * @param model the model
     * @return the view name for the patient dashboard
     */
    @GetMapping("/dashboard")
    public String patientDashboard(Model model) {
        model.addAttribute("specialities", Speciality.values());
        return "patients-dashboard";
    }

    /**
     * Searches for doctors by specialty.
     *
     * @param speciality the specialty
     * @param model      the model
     * @return the view name after searching for doctors
     * @throws EntityNotFoundException if the entity is not found
     */
    @GetMapping("/dashboard/search")
    public String searchDoctors(@RequestParam(required = false) Speciality speciality, Model model) throws EntityNotFoundException {
        try {
            model.addAttribute("doctors", doctorService.findDoctorBySpeciality(speciality));
            model.addAttribute("specialities", Speciality.values());
            model.addAttribute("searchPerformed", true);
        } catch (EntityNotFoundException e) {
            throw e;
        }
        return "patients-dashboard";
    }

    /**
     * Displays the patient's appointments.
     *
     * @param model the model
     * @return the view name for the appointments page
     * @throws EntityNotFoundException if the entity is not found
     */
    @GetMapping("/appointments")
    public String viewAppointments(Model model) throws EntityNotFoundException {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentByPatient();
            model.addAttribute("appointments", appointments);
        } catch (EntityNotFoundException e) {
            throw e;
        }
        return "appointments";
    }

    /**
     * Displays the form for updating the patient's profile.
     *
     * @param model the model
     * @return the view name for updating the patient's profile
     */
    @GetMapping("/profile/update")
    public String updateProfileForm(Model model) {
        PatientUpdateDTO patientUpdateDTO = new PatientUpdateDTO();
        model.addAttribute("patientUpdateDTO", patientUpdateDTO);
        return "update-patient-profile";
    }

    /**
     * Handles the profile update for a patient.
     *
     * @param patientUpdateDTO the patient update DTO
     * @param model            the model
     * @return the view name after updating the patient's profile
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("patientUpdateDTO") PatientUpdateDTO patientUpdateDTO, Model model) {
        try {
            patientService.updatePatient(patientUpdateDTO);
            model.addAttribute("successMessage", "Profile updated successfully!");
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "An error occurred while updating the profile: " + e.getMessage());
        }
        return "update-patient-profile";
    }
}
