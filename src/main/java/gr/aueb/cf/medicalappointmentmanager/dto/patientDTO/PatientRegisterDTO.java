package gr.aueb.cf.medicalappointmentmanager.dto.patientDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientRegisterDTO {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String ssn;
    private String email;
    private String phoneNumber;

}
