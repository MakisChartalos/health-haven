package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.mapper.Mapper;
import gr.aueb.cf.medicalappointmentmanager.model.Doctor;
import gr.aueb.cf.medicalappointmentmanager.model.Patient;
import gr.aueb.cf.medicalappointmentmanager.model.Speciality;
import gr.aueb.cf.medicalappointmentmanager.model.User;
import gr.aueb.cf.medicalappointmentmanager.repository.DoctorRepository;
import gr.aueb.cf.medicalappointmentmanager.repository.UserRepository;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.DoctorAlreadyExistsException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.medicalappointmentmanager.service.exceptions.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Service class for managing doctors within the Medical Appointment Management system.
 * This class handles all business logic related to doctor entities, including registration,
 * updates, deletion, and querying of doctor data.
 */
@Service
@Slf4j
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public Doctor getCurrentDoctor() throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EntityNotFoundException(Patient.class, null);
        }

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(Patient.class, null));
        return doctorRepository.findById(user.getDoctor().getId())
                .orElseThrow(() -> new EntityNotFoundException(Patient.class, null));
    }


    /**
     * Registers a new doctor in the system with the provided registration details.
     * The method ensures that both the username and medical license number are unique.
     * @param dto the registration data transfer object containing doctor's details.
     * @return the newly registered doctor.
     * @throws DoctorAlreadyExistsException if a doctor with the same license number already exists.
     * @throws UserAlreadyExistsException if a user with the same username already exists.
     */
    @Transactional
    @Override
    public Doctor registerDoctor(DoctorRegisterDTO dto) throws DoctorAlreadyExistsException, UserAlreadyExistsException {
        Doctor doctorToRegister;
        User userToRegister;


        try {
            doctorRepository.findDoctorByMedicalLicenseNumber(dto.getMedicalLicenseNumber())
                    .ifPresent(existingDoctor -> {
                        throw new DoctorAlreadyExistsException(dto.getMedicalLicenseNumber());
                    });
            userRepository.findUserByUsername(dto.getUsername())
                    .ifPresent(existingUser -> {
                        throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists.");
                    });

            doctorToRegister = Mapper.extractDoctorFromRegisterDTO(dto);
            userToRegister = Mapper.extractUserFromRegisterDTO(dto);
            userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

            doctorToRegister.addUser(userToRegister);
            doctorRepository.save(doctorToRegister);

            log.info("Doctor with medical license number: " + dto.getMedicalLicenseNumber() + " has been successfully registered.");

        } catch (DoctorAlreadyExistsException | UserAlreadyExistsException e) {
            log.error(e.getMessage());
            throw e;
        }
        return doctorToRegister;
    }


    /**
     * Updates an existing doctor's details in the system based on the provided data transfer object.
     * @param dto the data transfer object containing updated doctor details.
     * @return the updated doctor.
     * @throws EntityNotFoundException if the doctor to be updated is not found.
     */
    @Transactional
    @Override
    public Doctor updateDoctor(DoctorUpdateDTO dto) throws EntityNotFoundException {
        Doctor doctorToUpdate;

        try {
            doctorToUpdate = getCurrentDoctor();
            doctorToUpdate = doctorRepository.save(Mapper.mapToDoctor(doctorToUpdate, dto));
            log.info("Doctor with id: " + doctorToUpdate.getId() + " was updated");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return doctorToUpdate;
    }


    /**
     * Deletes a doctor from the system identified by the provided ID.
     * @param id the unique identifier of the doctor to be deleted.
     * @throws EntityNotFoundException if no doctor is found with the provided ID.
     */
    @Transactional
    @Override
    public void deleteDoctor(Long id) throws EntityNotFoundException {
        Doctor doctorToDelete;

        try {
            doctorToDelete = doctorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(Doctor.class, id));

            doctorRepository.deleteById(id);
            log.info("Doctor with id: " + id + " was deleted") ;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves a list of all doctors who have a last name starting with the specified string.
     * @param lastname the starting string of the doctor's last name to search for.
     * @return a list of doctors matching the criteria or an empty list if no matches are found.
     * @throws EntityNotFoundException if no doctors are found with the specified criteria.
     */
    @Override
    public List<Doctor> findAllDoctors(String lastname) throws EntityNotFoundException {
        List<Doctor> doctors;

        try {
            doctors = doctorRepository.findDoctorByLastnameStartingWith(lastname);
            if (doctors.isEmpty()) {
                throw new EntityNotFoundException(Doctor.class, 0L);
            }
            log.info("Doctors with lastname starting with " + lastname + " were found");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return doctors;
    }

    /**
     * Retrieves a doctor by their unique identifier.
     * @param id the unique identifier of the doctor to find.
     * @return the found doctor.
     * @throws EntityNotFoundException if no doctor is found with the provided ID.
     */
    @Override
    public Doctor getDoctorById(Long id) throws EntityNotFoundException {
        Doctor doctor;

        try {
            doctor = doctorRepository.findById(id)
                    .orElseThrow(() ->new EntityNotFoundException(Doctor.class, id));

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return doctor;
    }

    /**
     * Finds doctors by their specialty.
     * @param speciality the specialty to search for.
     * @return a list of doctors who specialize in the provided specialty or an empty list if no matches are found.
     * @throws EntityNotFoundException if no doctors are found with the specified specialty.
     */
    @Override
    public List<Doctor> findDoctorBySpeciality(Speciality speciality) throws EntityNotFoundException {
        List<Doctor> doctors;

        try {
            doctors = doctorRepository.findDoctorBySpeciality(speciality);
            if (doctors.isEmpty()) {
                throw new EntityNotFoundException(Doctor.class, 0L);
            }
            log.info("Doctors with speciality: " + speciality + " we found");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return doctors;
    }
}
