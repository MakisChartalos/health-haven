package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.healthhaven.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.healthhaven.mapper.Mapper;
import gr.aueb.cf.healthhaven.model.Patient;
import gr.aueb.cf.healthhaven.model.User;
import gr.aueb.cf.healthhaven.repository.PatientRepository;
import gr.aueb.cf.healthhaven.repository.UserRepository;

import gr.aueb.cf.healthhaven.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.healthhaven.service.exceptions.PatientAlreadyExistsException;
import gr.aueb.cf.healthhaven.service.exceptions.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing patients within the Medical Appointment Management system.
 * This class handles all business logic related to patient entities, including registration,
 * updates, deletion, and querying of patient data.
 */
@Service
@Slf4j
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    /**
     * Retrieves the currently authenticated patient from the security context.
     *
     * @return the current logged-in patient
     * @throws EntityNotFoundException if the patient is not found
     */
    public Patient getCurrentPatient() throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EntityNotFoundException(Patient.class, null);
        }

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(Patient.class, null));
        return patientRepository.findById(user.getPatient().getId())
                .orElseThrow(() -> new EntityNotFoundException(Patient.class, null));
    }



    /**
     * Registers a new patient in the system with the provided registration details.
     * The method ensures that both the SSN and username are unique.
     *
     * @param dto the registration data transfer object containing patient's details.
     * @return the newly registered patient.
     * @throws PatientAlreadyExistsException if a patient with the same SSN already exists.
     * @throws UserAlreadyExistsException    if a user with the same username already exists.
     */
    @Transactional
    @Override
    public Patient registerPatient(PatientRegisterDTO dto) throws PatientAlreadyExistsException, UserAlreadyExistsException {
        Patient patientToRegister;
        User userToRegister;


        try {
            patientRepository.findPatientBySsn(dto.getSsn())
                    .ifPresent(existingPatient -> {
                        throw new PatientAlreadyExistsException(dto.getSsn());
                    });
            userRepository.findUserByUsername(dto.getUsername())
                    .ifPresent(existingUser -> {
                        throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists.");
                    });

            patientToRegister = Mapper.extractPatientFromRegisterDTO(dto);
            userToRegister = Mapper.extractUserFromRegisterDTO(dto);
            userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

            patientToRegister.addUser(userToRegister);
            patientRepository.save(patientToRegister);
            log.info("Patient with SSN: " + dto.getSsn() + " has been successfully registered.");


        } catch (PatientAlreadyExistsException | UserAlreadyExistsException e) {
            log.error(e.getMessage());
            throw e;
        }
        return patientToRegister;

    }


    /**
     * Updates an existing patient's details in the system based on the provided data transfer object.
     *
     * @param dto the data transfer object containing updated patient details.
     * @return the updated patient.
     * @throws EntityNotFoundException if the patient to be updated is not found.
     */
    @Transactional
    @Override
    public Patient updatePatient(PatientUpdateDTO dto) throws EntityNotFoundException {
        Patient patientToUpdate;

        try {
            patientToUpdate = getCurrentPatient();
            patientToUpdate = patientRepository.save(Mapper.mapToPatient(patientToUpdate, dto));
            log.info("Patient with id: " + patientToUpdate.getId() + " was updated");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return patientToUpdate;
    }

    /**
     * Deletes a patient from the system identified by the provided ID.
     *
     * @param id the unique identifier of the patient to be deleted.
     * @return the deleted patient information.
     * @throws EntityNotFoundException if no patient is found with the provided ID.
     */
    @Transactional
    @Override
    public Patient deletePatient(Long id) throws EntityNotFoundException {
        Patient patientToDelete;

        try {
            patientToDelete = patientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(Patient.class, id));
            patientRepository.deleteById(id);
            log.info("Patient with id: " + id + " was deleted");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return patientToDelete;
    }


}

