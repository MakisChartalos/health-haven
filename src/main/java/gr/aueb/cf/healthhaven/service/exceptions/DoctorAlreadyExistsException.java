package gr.aueb.cf.healthhaven.service.exceptions;

public class DoctorAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DoctorAlreadyExistsException(String medicalLicenseNumber) {
        super("Doctor with medical license number: " + medicalLicenseNumber + " already exists" );
    }
}
