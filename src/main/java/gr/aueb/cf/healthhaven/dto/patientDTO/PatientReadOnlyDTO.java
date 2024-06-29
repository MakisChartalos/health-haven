package gr.aueb.cf.healthhaven.dto.patientDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientReadOnlyDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String ssn;
    private String email;
    private String phoneNumber;
}
