package gr.aueb.cf.healthhaven.service.exceptions;

public class PatientAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PatientAlreadyExistsException(String ssn) {
        super("Patient with ssn: " + ssn + " already exists" );
    }

}
