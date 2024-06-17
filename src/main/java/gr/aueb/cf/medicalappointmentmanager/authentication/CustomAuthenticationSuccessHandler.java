package gr.aueb.cf.medicalappointmentmanager.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * Custom authentication success handler to redirect users to different pages based on their roles.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Handles successful authentication and redirects users based on their roles.
     *
     * @param request        the request object.
     * @param response       the response object.
     * @param authentication the authentication object containing user details.
     * @throws IOException      if an input or output exception occurs.
     * @throws ServletException if a servlet-specific error occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("DOCTOR")) {
            response.sendRedirect("/doctors/dashboard");
        } else if (roles.contains("PATIENT")) {
            response.sendRedirect("/patients/dashboard");
        } else {
            response.sendRedirect("/default"); // Fallback URL in case no roles match
        }
    }
}
