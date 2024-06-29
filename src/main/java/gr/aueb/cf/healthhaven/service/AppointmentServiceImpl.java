package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.dto.appointmentDTO.AppointmentRegisterDTO;
import gr.aueb.cf.healthhaven.dto.appointmentDTO.AppointmentUpdateDTO;
import gr.aueb.cf.healthhaven.model.*;
import gr.aueb.cf.healthhaven.repository.AppointmentRepository;
import gr.aueb.cf.healthhaven.repository.DoctorRepository;
import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.healthhaven.service.exceptions.InvalidAppointmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for managing appointments within the Medical Appointment Management system.
 * This class handles all business logic related to appointment entities, including creation, updates,
 * deletion, and querying of appointment data.
 */
@Service
@Slf4j
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final IPatientService patientService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, IPatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientService = patientService;
    }

    /**
     * Validates and constructs the date and time for a new appointment.
     * Converts the provided year, month, day, hour, and minute into a LocalDateTime object and
     * validates that it falls within office hours.
     *
     * @param year   the year of the appointment
     * @param month  the month of the appointment
     * @param day    the day of the appointment
     * @param hour   the hour of the appointment
     * @param minute the minute of the appointment
     * @return the validated LocalDateTime of the appointment
     * @throws InvalidAppointmentException if the appointment time is outside office hours
     */
    private LocalDateTime validateAndGetAppointmentDateTime(int year, int month, int day, int hour, int minute) throws InvalidAppointmentException {
        LocalDateTime appointmentDateTime = LocalDateTime.of(year, month, day, hour, minute);

        int openingHour = 9;
        int closingHour = 21;

        LocalDateTime officeOpeningTime = LocalDateTime.of(year, month, day, openingHour, 0);
        LocalDateTime officeClosingTime = LocalDateTime.of(year, month, day, closingHour, 0);

        if (appointmentDateTime.isBefore(officeOpeningTime) || appointmentDateTime.isAfter(officeClosingTime.minusMinutes(10))) {
            throw new InvalidAppointmentException("Appointment time is outside of office hours.");
        }

        return appointmentDateTime;
    }

    /**
     * Checks if the appointment time is free (i.e., the doctor doesn't have an appointment at that time).
     *
     * @param dateTime the date and time of the appointment
     * @param doctorId the ID of the doctor
     * @return true if the appointment time is free, false otherwise
     */
    private boolean isAppointmentTimeFree(LocalDateTime dateTime, Long doctorId) {
        LocalDateTime appointmentEndTime = dateTime.plusHours(1);
        List<Appointment> appointments = appointmentRepository
                .findAppointmentByDoctorIdAndAppointmentDateTimeBetweenAndStatusNot(
                        doctorId, dateTime, appointmentEndTime, AppointmentStatus.CANCELLED);

        return appointments.isEmpty();
    }


    /**
     * Creates a new appointment.
     *
     * @param dto the data transfer object containing the appointment details
     * @return the created appointment
     * @throws InvalidAppointmentException if the appointment time is not available or outside office hours
     * @throws EntityNotFoundException     if the doctor or patient does not exist
     */
    @Override
    public Appointment createAppointment(AppointmentRegisterDTO dto) throws InvalidAppointmentException, EntityNotFoundException {
        Appointment appointment = new Appointment();
        Doctor doctor;

        try {
            doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new EntityNotFoundException(Doctor.class, dto.getDoctorId()));
            LocalDateTime appointmentDateTime = validateAndGetAppointmentDateTime(dto.getYear(), dto.getMonth(), dto.getDay(), dto.getHour(), 0);
            if (!isAppointmentTimeFree(appointmentDateTime, dto.getDoctorId())) {
                throw new InvalidAppointmentException("The appointment time is not available.");
            }
            appointment.setPatient(patientService.getCurrentPatient());
            appointment.setDoctor(doctor);
            appointment.setAppointmentDateTime(appointmentDateTime);

            appointmentRepository.save(appointment);
            log.info("Appointment with id: " + appointment.getId() + " has been successfully created");
        } catch (InvalidAppointmentException | EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return appointment;
    }

    /**
     * Updates an existing appointment.
     *
     * @param dto the data transfer object containing the updated appointment details
     * @return the updated appointment
     * @throws EntityNotFoundException     if the appointment does not exist
     * @throws InvalidAppointmentException if the appointment time is not available or outside office hours
     */
    @Override
    public Appointment updateAppointment(AppointmentUpdateDTO dto) throws EntityNotFoundException, InvalidAppointmentException {
        Appointment appointmentToUpdate;

        try {
            appointmentToUpdate = appointmentRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(Appointment.class, dto.getId()));
            LocalDateTime appointmentDateTime = validateAndGetAppointmentDateTime(dto.getYear(), dto.getMonth(), dto.getDay(), dto.getHour(), 0);
            if (!isAppointmentTimeFree(appointmentDateTime, dto.getDoctorId())) {
                throw new InvalidAppointmentException("The appointment time is not available.");
            }

            appointmentToUpdate.setAppointmentDateTime(appointmentDateTime);
            appointmentRepository.save(appointmentToUpdate);
        } catch (EntityNotFoundException | InvalidAppointmentException e) {
            log.error(e.getMessage());
            throw e;
        }
        return appointmentToUpdate;
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the ID of the appointment to delete
     * @throws EntityNotFoundException if the appointment does not exist
     */
    @Override
    public void deleteAppointmentById(Long id) throws EntityNotFoundException {
        Appointment appointmentToDelete;
        try {
            appointmentToDelete = appointmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(Appointment.class, id));
            appointmentRepository.deleteById(id);
            log.info("Appointment with id: " + id + " was successfully deleted");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment to retrieve
     * @return the retrieved appointment
     * @throws EntityNotFoundException if the appointment does not exist
     */
    @Override
    public Appointment getAppointmentById(Long id) throws EntityNotFoundException {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class, id));
    }

    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @return the list of appointments for the specified doctor
     * @throws EntityNotFoundException if the doctor does not exist
     */
    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) throws EntityNotFoundException {
        List<Appointment> appointments;

        try {
            doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new EntityNotFoundException(Doctor.class, doctorId));
            appointments = appointmentRepository.findAllByDoctorId(doctorId);
            if (appointments.isEmpty()) {
                log.info("No appointments found for doctor with id: " + doctorId);
            } else {
                log.info("Appointments for doctor with id: " + doctorId + " were found");
            }
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }

        return appointments;
    }

    /**
     * Retrieves all appointments for the current logged-in patient.
     *
     * @return the list of appointments for the current patient
     * @throws EntityNotFoundException if the patient does not exist
     */
    @Override
    public List<Appointment> getAppointmentByPatient() throws EntityNotFoundException {
        Patient patient;
        List<Appointment> appointments;
        try {
            patient = patientService.getCurrentPatient();
            appointments = appointmentRepository.findAllByPatientId(patient.getId());

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return appointments;
    }

    /**
     * Updates the status of an appointment.
     *
     * @param appointmentId the ID of the appointment to update
     * @param status        the new status of the appointment
     * @return the updated appointment
     * @throws EntityNotFoundException if the appointment does not exist
     */
    @Override
    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status) throws EntityNotFoundException {
        Appointment appointmentToUpdate = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class, appointmentId));

        appointmentToUpdate.setStatus(status);
        appointmentRepository.save(appointmentToUpdate);
        log.info("Appointment with id: " + appointmentToUpdate.getId() + " has been successfully updated with new status: " + status);
        return appointmentToUpdate;
    }
}
