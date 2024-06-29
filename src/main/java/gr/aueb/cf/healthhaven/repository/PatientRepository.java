package gr.aueb.cf.healthhaven.repository;

import gr.aueb.cf.healthhaven.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientBySsn(String ssn);
}
