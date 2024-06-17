package gr.aueb.cf.medicalappointmentmanager.mapper;

import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientRegisterDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientUpdateDTO;
import gr.aueb.cf.medicalappointmentmanager.model.Doctor;
import gr.aueb.cf.medicalappointmentmanager.model.Patient;
import gr.aueb.cf.medicalappointmentmanager.model.User;
import lombok.NoArgsConstructor;

/**
 * Utility class providing static methods to map DTOs to domain models.
 */
@NoArgsConstructor
public class Mapper {

    /**
     * Converts a DoctorRegisterDTO to a Doctor entity.
     *
     * @param dto The DoctorRegisterDTO object containing data to create a Doctor entity.
     * @return A new Doctor object populated with data from the given DTO.
     */
    public static Doctor extractDoctorFromRegisterDTO(DoctorRegisterDTO dto) {
        return new Doctor(dto.getFirstname(), dto.getLastname(), dto.getSpeciality(), dto.getMedicalLicenseNumber());
    }

    /**
     * Converts a PatientRegisterDTO to a Patient entity.
     *
     * @param dto The PatientRegisterDTO object containing data to create a Patient entity.
     * @return A new Patient object populated with data from the given DTO.
     */
    public static Patient extractPatientFromRegisterDTO(PatientRegisterDTO dto) {
        return new Patient(dto.getFirstname(), dto.getLastname(), dto.getSsn(), dto.getEmail(), dto.getPhoneNumber());
    }

    /**
     * Converts a specific registration DTO to a User entity.
     * This method determines whether the DTO provided is for registering a doctor or a patient,
     * and creates a User entity with the appropriate role and credentials.
     *
     * @param dto A specific registration DTO, either DoctorRegisterDTO or PatientRegisterDTO,
     *            containing the necessary user information and intended role.
     * @return A User object initialized with credentials and roles based on the provided DTO.
     * @throws IllegalArgumentException If the provided DTO is not an instance of DoctorRegisterDTO or PatientRegisterDTO,
     *                                  indicating an unsupported or incorrect DTO type.
     */
    public static User extractUserFromRegisterDTO(Object dto) {
        if (dto instanceof DoctorRegisterDTO doctorDTO) {
            return User.getNewUserWithDoctorRole(doctorDTO.getUsername(), doctorDTO.getPassword());
        } else if (dto instanceof PatientRegisterDTO patientDTO) {
            return User.getNewUserWithPatientRole(patientDTO.getUsername(), patientDTO.getPassword());
        }
        throw new IllegalArgumentException("Unknown DTO type");
    }

    /**
     * Maps the fields of a DoctorUpdateDTO to an existing Doctor entity.
     *
     * @param doctor The existing Doctor entity to be updated.
     * @param dto    The DoctorUpdateDTO containing updated data.
     * @return The updated Doctor entity.
     */
    public static Doctor mapToDoctor(Doctor doctor, DoctorUpdateDTO dto) {
        doctor.setId(doctor.getId());
        doctor.setFirstname(dto.getFirstname());
        doctor.setLastname(dto.getLastname());
        doctor.setSpeciality(dto.getSpeciality());
        return doctor;
    }

    /**
     * Maps the fields of a PatientUpdateDTO to an existing Patient entity.
     *
     * @param patient The existing Patient entity to be updated.
     * @param dto     The PatientUpdateDTO containing updated data.
     * @return The updated Patient entity.
     */
    public static Patient mapToPatient(Patient patient, PatientUpdateDTO dto) {
        patient.setId(patient.getId());
        patient.setFirstname(dto.getFirstname());
        patient.setLastname(dto.getLastname());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        return patient;
    }
}
