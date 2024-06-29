package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.dto.appointmentDTO.AppointmentRegisterDTO;
import gr.aueb.cf.healthhaven.dto.appointmentDTO.AppointmentUpdateDTO;
import gr.aueb.cf.healthhaven.model.Appointment;
import gr.aueb.cf.healthhaven.model.AppointmentStatus;
import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.healthhaven.service.exceptions.InvalidAppointmentException;

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
