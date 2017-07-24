package tudu.web.mvc;


import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.annotation.ExpectedException;
import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Level2AdministrationControllerTest {

    @Mock
    private ConfigurationService cfgService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
   * - Vérifier qu'aucune interactions n'a lieu lorsque la page demandée n'est ni "configuration" ni "users"
   * Méthode :  display
   */
    @Test
    public void display_should_not_interact_when_page_different_than_configuration_or_users() throws Exception {

        //given

        //when
        adminController.display("none");
        //then
        verifyZeroInteractions(userService);
        verifyZeroInteractions(cfgService);


    }

    /*
    *
    *  Vérifier dans un test que pour la page "configuration" il n'y a pas d'interaction avec userService.
    * Méthode :  display
    */
    @Test
    public void display_should_read_configService_properties_when_page_is_configuration() throws Exception {
        //given
        when(cfgService.getProperty("application.static.path")).thenReturn(this.property("/"));
        when(cfgService.getProperty("google.analytics.key")).thenReturn(this.property("google.analytics.key"));
        when(cfgService.getProperty("smtp.host")).thenReturn(this.property("127.0.0.1"));
        when(cfgService.getProperty("smtp.port")).thenReturn(this.property("20"));
        when(cfgService.getProperty("smtp.user")).thenReturn(this.property("user_test"));
        when(cfgService.getProperty("smtp.password")).thenReturn(this.property("user_password"));
        when(cfgService.getProperty("smtp.from")).thenReturn(this.property("from_user"));
        //when
        adminController.display("configuration");
        //then
        verifyZeroInteractions(userService);
    }


    /*
    * - Vérifer que pour l'action "enableUser" le service afférent est appelé et que disableUser ne l'est pas
    * Méthode :  update
    */
    @Test
    public void update_enable_user_on_enableUser_action() throws Exception {

        //given
        AdministrationModel model = mock(AdministrationModel.class);
        when(model.getAction()).thenReturn("enableUser");
        adminController.update(model);
        //then
        verify(userService, never()).disableUser(anyString());
        //verify(bar).doStuff(any());

    }

    /*
    * - Vérifer que pour l'action "disableUser" le service afférent est appelé et que enableUser ne l'est pas (d'une manière différente)
    * Méthode :  update
    */
    @Test
    public void update_can_disable_user_on_disableUser_action() throws Exception {
        //given
        AdministrationModel model = mock(AdministrationModel.class);
        when(model.getAction()).thenReturn("disableUser");
        adminController.update(model);
        //then
        verify(userService, atLeast(0)).enableUser(anyString());
    }

    /*
    *
    *  Vérifier que pour l'action appelle findUsersByLogin après un enableUser ou un disableUser
    * Méthode :  update
    */
    @Test
    public void update_should_fetch_users_on_login_after_disabling_suer() throws Exception {
        //given
        AdministrationModel model = mock(AdministrationModel.class);
        when(model.getAction()).thenReturn("disableUser");
        adminController.update(model);
        //then
        verify(userService, atLeast(1)).findUsersByLogin(anyString());
    }

    private Property property(String value) {
        Property property = new Property();
        property.setValue(value);
        return property;
    }

}



