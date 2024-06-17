package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.model.Patient;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.PatientAlreadyExistsException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.UserAlreadyExistsException;

public interface IPatientService {

    Patient registerPatient(PatientRegisterDTO dto) throws PatientAlreadyExistsException, UserAlreadyExistsException;
    Patient updatePatient(PatientUpdateDTO dto) throws EntityNotFoundException;
    Patient deletePatient(Long id) throws EntityNotFoundException;

    Patient getCurrentPatient()throws EntityNotFoundException;




}
