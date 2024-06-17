package gr.aueb.cf.medicalappointmentmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "PATIENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "SSN", nullable = false, unique = true)
    private String ssn;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONENUMBER", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    public Patient(String firstname, String lastname, String ssn, String email, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addUser(User user) {
        this.user = user;
        user.setPatient(this);
    }


    // Adds an appointment to the patient's set of appointments
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setPatient(this);
    }

    // Removes an appointment from the patient's set of appointments
    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setPatient(null);
    }
}
