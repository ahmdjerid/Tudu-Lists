package tudu.security;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;

import static org.easymock.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;


public class Level3UserDetailsServiceImplMockitoTest {

    /* Le test suivant a été ecris avec EasyMock. L'écrire à nouveau en utilisant la syntaxe BDD Mockito et en utilisant
     les assertions de fest assert a la place des assertEquals * assertNotNull*/


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
        user.setEnabled(true);
        Role userRole = new Role();
        userRole.setRole(RolesEnum.ROLE_USER.toString());
        user.getRoles().add(userRole);

    }

    @Test
    public void testLoadUserByUsername_BDD() {

        //given
        given(userService.findUser("test_user")).willReturn(user);

        //when
        UserDetails springSecurityUser = userDetailsService.loadUserByUsername("test_user");

        //then
        assertThat(springSecurityUser.getUsername()).isEqualTo(user.getLogin());
        assertThat(springSecurityUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(springSecurityUser.getAuthorities()).hasSize(1);
        assertThat(springSecurityUser.getAuthorities().iterator().next().getAuthority())
                .isEqualTo(RolesEnum.ROLE_USER.toString());


    }

    // Pour aller plus loin : Should you only mock types that you own ?: http://stackoverflow.com/questions/1906344/should-you-only-mock-types-you-own

    @Ignore
    public void testLoadUserByUsername() {

        UserDetailsServiceImpl authenticationDAO = new UserDetailsServiceImpl();
        UserService userService = EasyMock.createMock(UserService.class);
        ReflectionTestUtils.setField(authenticationDAO, "userService", userService);

        expect(userService.findUser("test_user")).andReturn(user);
        replay(userService);

        UserDetails springSecurityUser = authenticationDAO
                .loadUserByUsername("test_user");

        assertEquals(user.getLogin(), springSecurityUser.getUsername());
        assertEquals(user.getPassword(), springSecurityUser.getPassword());
        assertNotNull(user.getLastAccessDate());
        assertEquals(1, springSecurityUser.getAuthorities().size());
        assertEquals(RolesEnum.ROLE_USER.toString(),
                springSecurityUser.getAuthorities().iterator().next().getAuthority());

        verify(userService);
    }
}
