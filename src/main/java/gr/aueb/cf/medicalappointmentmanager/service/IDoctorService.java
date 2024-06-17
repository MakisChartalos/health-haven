package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.model.Doctor;
import gr.aueb.cf.medicalappointmentmanager.model.Speciality;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.DoctorAlreadyExistsException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.UserAlreadyExistsException;

import javax.print.Doc;
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
