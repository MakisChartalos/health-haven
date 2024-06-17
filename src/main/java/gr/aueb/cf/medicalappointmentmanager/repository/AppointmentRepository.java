package gr.aueb.cf.medicalappointmentmanager.repository;

import gr.aueb.cf.medicalappointmentmanager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment>  findAppointmentByPatientFirstnameAndPatientLastname(String firstname, String lastname);
    List<Appointment> findAllByDoctorId(Long id);
    List<Appointment> findAllByPatientId(Long id);
    List<Appointment> findAppointmentByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);

}
