package tudu.web.mvc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockitoLogger;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import tudu.domain.Property;
import tudu.domain.User;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Level1AdministrationControllerTest {

    @Mock
    private ConfigurationService cfgService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
    * Vérifier dans un test que pour la page "configuration" les propriétés smtp (et uniquement celles là) soit donnée au model
    * Méthode :  display
    */
    @Test
    public void display_should_put_smtp_config_properties_in_admin_model_when_page_is_configuration() throws Exception {

        // given
        when(cfgService.getProperty("application.static.path")).thenReturn(this.property("/"));
        when(cfgService.getProperty("google.analytics.key")).thenReturn(this.property("google.analytics.key"));
        when(cfgService.getProperty("smtp.host")).thenReturn(this.property("127.0.0.1"));
        when(cfgService.getProperty("smtp.port")).thenReturn(this.property("20"));
        when(cfgService.getProperty("smtp.user")).thenReturn(this.property("user_test"));
        when(cfgService.getProperty("smtp.password")).thenReturn(this.property("user_password"));
        when(cfgService.getProperty("smtp.from")).thenReturn(this.property("from_user"));
        // when
        ModelAndView display = adminController.display("configuration");
        AdministrationModel model = (AdministrationModel) display.getModel().get("administrationModel");

        // then
        assertEquals(model.getSmtpHost(), "127.0.0.1");
        assertEquals(model.getSmtpPort(), "20");
        assertEquals(model.getSmtpUser(), "user_test");
        assertEquals(model.getSmtpPassword(), "user_password");
        assertEquals(model.getSmtpFrom(), "from_user");
    }

    /*
   * Vérifier que l update ne retourne pas un modele null
   * */
    @Test
    public void update_shouldnt_return_a_null_model() throws Exception {
        final User user = new User();
        //given
        AdministrationModel model = mock(AdministrationModel.class);
        when(model.getAction()).thenReturn("disableUser");
        when(model.getLogin()).thenReturn("test_user");

        when(this.userService.findUsersByLogin("test_user")).
                thenReturn(new ArrayList<User>() {{
                    add(user);
                }});
        //when
        ModelAndView modelAndViewResponse = adminController.update(model);

        //then
        assertNotNull(modelAndViewResponse);
        assertNotNull(modelAndViewResponse.getViewName());
        assertEquals("users", modelAndViewResponse.getModel().get("page"));


    }


    private Property property(String value) {
        Property property = new Property();
        property.setValue(value);
        return property;
    }
}
