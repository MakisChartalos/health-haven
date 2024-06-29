package gr.aueb.cf.healthhaven.repository;

import gr.aueb.cf.healthhaven.model.Appointment;
import gr.aueb.cf.healthhaven.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment>  findAppointmentByPatientFirstnameAndPatientLastname(String firstname, String lastname);
    List<Appointment> findAllByDoctorId(Long id);
    List<Appointment> findAllByPatientId(Long id);
    List<Appointment> findAppointmentByDoctorIdAndAppointmentDateTimeBetweenAndStatusNot(Long doctorId, LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status);

}
