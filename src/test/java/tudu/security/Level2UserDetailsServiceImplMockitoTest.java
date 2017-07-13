package tudu.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tudu.service.UserService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//Niveau2
public class Level2UserDetailsServiceImplMockitoTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(userService.findUser("test_user")).thenThrow(new ObjectRetrievalFailureException("msg", new Throwable()));

    }
    /*
    * Simuler une levée d'exceptions - tester que la methode lève bien une UsernameNotFoundException si la méthode
    * findBy lève une ObjectRetrievalFailureException
    * Méhode : loadUserByUsername

    */

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsername_throw_UsernameNotFoundException() {
        userDetailsService.loadUserByUsername("test_user");


    }
}
