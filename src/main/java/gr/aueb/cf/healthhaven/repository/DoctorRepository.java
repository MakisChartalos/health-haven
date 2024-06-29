package gr.aueb.cf.healthhaven.repository;

import gr.aueb.cf.healthhaven.model.Doctor;
import gr.aueb.cf.healthhaven.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorByMedicalLicenseNumber(String medicalLicenseNumber);
    List<Doctor> findDoctorBySpeciality(Speciality speciality);
    List<Doctor> findDoctorByLastnameStartingWith(String lastname);

}
