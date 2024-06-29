package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorRegisterDTO;
import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorUpdateDTO;
import gr.aueb.cf.healthhaven.model.Doctor;
import gr.aueb.cf.healthhaven.model.Speciality;
import gr.aueb.cf.healthhaven.service.exceptions.DoctorAlreadyExistsException;
import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.healthhaven.service.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface IDoctorService {

    Doctor registerDoctor(DoctorRegisterDTO dto) throws DoctorAlreadyExistsException, UserAlreadyExistsException;
    Doctor updateDoctor(DoctorUpdateDTO dto) throws EntityNotFoundException;
    void deleteDoctor(Long id) throws EntityNotFoundException;
    List<Doctor> findAllDoctors(String lastname) throws EntityNotFoundException;
    Doctor getDoctorById(Long id) throws EntityNotFoundException;
    List<Doctor> findDoctorBySpeciality(Speciality speciality) throws EntityNotFoundException;

    Doctor getCurrentDoctor() throws EntityNotFoundException;
}
