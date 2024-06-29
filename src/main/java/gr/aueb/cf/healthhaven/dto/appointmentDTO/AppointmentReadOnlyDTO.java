package gr.aueb.cf.healthhaven.dto.appointmentDTO;

import gr.aueb.cf.healthhaven.dto.doctorDTO.DoctorReadOnlyDTO;
import gr.aueb.cf.healthhaven.dto.patientDTO.PatientReadOnlyDTO;
import gr.aueb.cf.healthhaven.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentReadOnlyDTO {

    private Long id;
    private DoctorReadOnlyDTO doctorInfo;
    private PatientReadOnlyDTO patientInfo;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
