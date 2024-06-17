package gr.aueb.cf.medicalappointmentmanager.dto.patientDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientUpdateDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;

}


