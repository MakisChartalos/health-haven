package gr.aueb.cf.medicalappointmentmanager.dto.appointmentDTO;

import gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO.DoctorReadOnlyDTO;
import gr.aueb.cf.medicalappointmentmanager.dto.patientDTO.PatientReadOnlyDTO;
import gr.aueb.cf.medicalappointmentmanager.model.AppointmentStatus;
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
