package gr.aueb.cf.healthhaven.dto.doctorDTO;

import gr.aueb.cf.healthhaven.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorUpdateDTO {

    private long id;
    private String firstname;
    private String lastname;
    private Speciality speciality;
}
