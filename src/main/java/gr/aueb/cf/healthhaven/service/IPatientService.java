package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.healthhaven.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.healthhaven.model.Patient;
import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.healthhaven.service.exceptions.PatientAlreadyExistsException;
import gr.aueb.cf.healthhaven.service.exceptions.UserAlreadyExistsException;

public interface IPatientService {

    Patient registerPatient(PatientRegisterDTO dto) throws PatientAlreadyExistsException, UserAlreadyExistsException;
    Patient updatePatient(PatientUpdateDTO dto) throws EntityNotFoundException;
    Patient deletePatient(Long id) throws EntityNotFoundException;

    Patient getCurrentPatient()throws EntityNotFoundException;




}
