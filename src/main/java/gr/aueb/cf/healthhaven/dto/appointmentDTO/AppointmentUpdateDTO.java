package gr.aueb.cf.healthhaven.dto.appointmentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentUpdateDTO {
    private Long id;
    private Long doctorId;
    private int year;
    private int month;
    private int day;
    private int hour;
}
