package gr.aueb.cf.medicalappointmentmanager.repository;

import gr.aueb.cf.medicalappointmentmanager.model.Doctor;
import gr.aueb.cf.medicalappointmentmanager.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorByMedicalLicenseNumber(String medicalLicenseNumber);
    List<Doctor> findDoctorBySpeciality(Speciality speciality);
    List<Doctor> findDoctorByLastnameStartingWith(String lastname);

}
