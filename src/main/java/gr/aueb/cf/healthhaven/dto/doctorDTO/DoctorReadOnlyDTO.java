package gr.aueb.cf.healthhaven.dto.doctorDTO;

import gr.aueb.cf.healthhaven.model.Speciality;
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
