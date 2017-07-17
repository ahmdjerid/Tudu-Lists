package tudu.service.impl;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import tudu.domain.TodoList;
import tudu.domain.User;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class Level3UserServiceImplMockitoTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    User user;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setLogin("test_user");
        user.setFirstName("First name");
        user.setLastName("Last name");
    }


    /*
    Vérifier que la liste todo a bien pour name Welcome!
    Méthode :  createNewTodoList
    */
    @Test
    public void itShould_create_todo_list() {
        //given

        //when
        TodoList newTodoList = userService.createNewTodoList(user);
        //then
        Assertions.assertThat(newTodoList.getName()).isNotNull();
        Assertions.assertThat(newTodoList.getName()).isEqualTo("Welcome!");


    }
    /*
    Vérifier que la liste todo a bien pour name Welcome! - autre méthode
    Méthode :  createNewTodoList
    */
    @Test
    public void itShould_create_todo_list_another_way() {
        //given

        //when
        TodoList newTodoList = userService.createNewTodoList(user);
        //then
        assertNotNull(newTodoList);
        assertEquals(newTodoList.getName(),"Welcome!");

    }
}
