package tudu.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import tudu.domain.User;
import tudu.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class Level1UserDetailsServiceImplMockitoTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    private User user;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setLogin("test_user");
        user.setPassword("password");
        when(userService.findUser("test_user")).thenReturn(user);


    }

    /*
    * Type : Test état
    * Vérifier que la méthode loadByUsername renvoie le bon login/password/les bonnes autoritées correspondant a l User renvoyé par le mock de userService.findUser
    * Méhode : loadUserByUsername
   */
    @Test
    public void userDetails_should_correspond_to_the_user_found() {

        UserDetails foundUser = userDetailsService.loadUserByUsername("test_user");
        assertEquals(foundUser.getUsername(), user.getLogin());
        assertEquals(foundUser.getPassword(), user.getPassword());
    }
}
