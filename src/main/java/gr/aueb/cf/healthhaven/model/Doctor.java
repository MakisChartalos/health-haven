package gr.aueb.cf.healthhaven.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DOCTORS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FIRSTNAME" ,nullable = false)
    private String firstname;

    @Column(name = "LASTNAME" ,nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "SPECIALITY", nullable = false)
    private Speciality speciality;

    @Column(name = "MEDICAL_LICENSE_NUMBER", nullable = false , unique = true)
    private String medicalLicenseNumber;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    public Doctor(String firstname, String lastname, Speciality speciality, String medicalLicenseNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.speciality = speciality;
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public void addUser(User user) {
        this.user = user;
        user.setDoctor(this);
    }


    // Adds an appointment to the doctor's set of appointments
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setDoctor(this);
    }

    // Removes an appointment from the doctor's set of appointments
    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setDoctor(null);
    }


}
