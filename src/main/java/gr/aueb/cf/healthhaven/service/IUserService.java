package gr.aueb.cf.healthhaven.service;

import gr.aueb.cf.healthhaven.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {
    User getUserByUsername(String username) throws UsernameNotFoundException;
}
