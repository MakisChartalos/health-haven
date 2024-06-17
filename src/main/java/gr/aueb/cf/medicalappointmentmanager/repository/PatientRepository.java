package gr.aueb.cf.medicalappointmentmanager.repository;

import gr.aueb.cf.medicalappointmentmanager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientBySsn(String ssn);
}
