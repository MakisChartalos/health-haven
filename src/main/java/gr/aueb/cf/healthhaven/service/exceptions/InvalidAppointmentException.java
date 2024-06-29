package gr.aueb.cf.healthhaven.service.exceptions;

public class InvalidAppointmentException extends Exception {

    private static final long serialUID = 1L;

    public InvalidAppointmentException(String message) {
        super(message);
    }
}
