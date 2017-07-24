package tudu.web.mvc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;
import tudu.service.impl.ConfigurationServiceImpl;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class Level3AdministrationControllerTest {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ConfigurationService cfgServiceSpy;

    // @Autowired
    // private ConfigurationService configurationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();

    /*
    *  Vérifier La réponse par défaut du mock configService et faire un test avec la page configuration
    *  Méthode :  display
    *  Aide : configuaration du mock a l'aide de RETURNS_SMART_NULLS
    */
    @Test
    public void display_should_set_default_configuration() throws Exception {
        //given
        // given
        when(cfgServiceSpy.getProperty("application.static.path")).thenReturn(this.property("/"));
        when(cfgServiceSpy.getProperty("google.analytics.key")).thenReturn(this.property("google.analytics.key"));
        when(cfgServiceSpy.getProperty("smtp.host")).thenReturn(this.property("127.0.0.1"));
        when(cfgServiceSpy.getProperty("smtp.port")).thenReturn(this.property("20"));
        when(cfgServiceSpy.getProperty("smtp.user")).thenReturn(this.property("user_test"));
        when(cfgServiceSpy.getProperty("smtp.password")).thenReturn(this.property("user_password"));
        when(cfgServiceSpy.getProperty("smtp.from")).thenReturn(this.property("from_user"));
        //when
        adminController.display("configuration");
        //then
    }


    /*
    * Vérifier que le configService.updateEmailProperties est bien appelé en ne vérifiant que les valeurs user et password .
    * Aide : Spy
   * Méthode :  update
    */
    @Test
    public void should_update_email_Property_for_the_right_user() throws Exception {

        //given
        AdministrationModel model = new AdministrationModel();
        model.setAction("configuration");
        model.setLogin("test_user");

        ConfigurationService myClassSpy = spy(new ConfigurationServiceImpl());

        Mockito.doNothing().when(myClassSpy)
                .updateEmailProperties("mmpl", "test_user", "test_user", "test_password", "pl");
        //when
        adminController.update(model);
        //then
    }


/*
* Reprendre sur quelques tests ayant des assertEquals, assertNull, assertNotNull avec le framefork fest assert
*/


/*
* Reprendre sur quelques tests ayant des when, verify, doThrow en utilisant la syntaxe bdd mockito
*/

    private Property property(String value) {
        Property property = new Property();
        property.setValue(value);
        return property;
    }
}
