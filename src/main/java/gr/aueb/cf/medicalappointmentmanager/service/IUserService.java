package gr.aueb.cf.medicalappointmentmanager.service;

import gr.aueb.cf.medicalappointmentmanager.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {
    User getUserByUsername(String username) throws UsernameNotFoundException;
}
