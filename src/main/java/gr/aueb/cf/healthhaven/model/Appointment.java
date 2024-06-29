package gr.aueb.cf.healthhaven.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "APPOINTMENTS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    @NotNull
    private Doctor doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    @NotNull
    private Patient patient;

    @Column(name = "DATE" ,nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

}
