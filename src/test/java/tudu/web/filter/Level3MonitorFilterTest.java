package tudu.web.filter;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Level3MonitorFilterTest {


    @InjectMocks
    MonitorFilter monitorFilterMock = new MonitorFilter();

    @Mock
    SecurityHelper securityHelperMock;

    @Mock
    ServletRequest httpServletRequestMock;

    @Mock
    ServletResponse httpServletResponseMock;

    @Mock
    FilterChain filterChainMock;

    @Mock
    Authentication authenticationMock;


    @Mock
    Log logMock;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        monitorFilterMock.setMonitored(true);
    }

    /*
  Verifier que l on passe bien dans les branches
   *               if (principal instanceof String) {
                       userName = "anonymous";
                   } else {
                       User springSecurityUser = (User) principal;
                       userName = springSecurityUser.getUsername();
                   }

    *
    * Duree max : 10 minutes
    */
    @Test
    public void test_authentication_failure() throws ServletException, IOException {

        //given
        when(securityHelperMock.getAuthentication()).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn("anonymous");

        //when
        monitorFilterMock.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        //then
        verify(logMock).debug(matches(".*anonymous.*"));
    }

    @Test
    public void test_authentication_success() throws ServletException, IOException {
        User user = new User("user_test", "password",
                true, true, false,
                false, new ArrayList<GrantedAuthority>());

        //given

        when(securityHelperMock.getAuthentication()).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(user);

        //when
        monitorFilterMock.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);
        //then
        verify(logMock).debug(matches(".*user_test.*"));


    }
}
