package gr.aueb.cf.medicalappointmentmanager.dto.doctorDTO;

import gr.aueb.cf.medicalappointmentmanager.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorReadOnlyDTO {
    private String firstname;
    private String lastname;
    private Speciality speciality;
}
