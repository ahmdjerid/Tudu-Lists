package tudu.web.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Ahmed Jerid <ahmed.jerid@olbati.com>
 *         Date: 18/07/2017
 */
class SecurityHelper {

    Authentication authentication;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
