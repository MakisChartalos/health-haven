package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.dto.appointmentDTO.AppointmentRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.appointmentDTO.AppointmentUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.model.Appointment;
import gr.aueb.cf.medicalappointmentmanager.model.AppointmentStatus;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.InvalidAppointmentException;

import java.util.List;

public interface IAppointmentService {

    Appointment createAppointment(AppointmentRegisterDTO dto) throws InvalidAppointmentException, EntityNotFoundException;
    Appointment updateAppointment(AppointmentUpdateDTO dto) throws InvalidAppointmentException, EntityNotFoundException;
    void deleteAppointmentById(Long id) throws EntityNotFoundException;
    Appointment getAppointmentById(Long id) throws EntityNotFoundException;
    List<Appointment> getAppointmentsByDoctorId(Long doctorId) throws EntityNotFoundException;
    List<Appointment> getAppointmentByPatient() throws EntityNotFoundException;
    Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status) throws EntityNotFoundException;





}
