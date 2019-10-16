package com.ZIRO.todoListapi.Controllers;

import com.ZIRO.todoListapi.Entities.User;
import com.ZIRO.todoListapi.Repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    public UserController userController;

    @Mock
    public UserRepository userRepository;

    @Before
    public void initalize() {
        userController = new UserController(userRepository);
    }

    @Test
    public void shouldGetUserAsMe () {
        ResponseEntity<Map<String, Object>> response =  userController.getMe();
        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Map<String, Object> results = response.getBody();
        assertNotNull(results);
        assert(results.containsKey("me"));
        assert(results.get("me").equals("Ahmed"));

    }

    @Test
    public void shouldSuccessReturnCurrentUserByUserId () {
        User user = new User();
        user.setFirstName("SOFY");
        user.setLastName("ASDY");
        user.setEmail("Sofy@mylove.com");
        user.setPhone("112236");

        Map<String, String> obj = new HashMap<>();
        obj.put("user_id", "123");
        when(userRepository.findOneByUserId(any (Long.class))).thenReturn(user);

        ResponseEntity<User> response = userController.getOne(obj);

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        User resultUser = response.getBody();

        assertNotNull(resultUser);
        assertEquals("SOFY", resultUser.getFirstName());
        assertEquals("ASDY", resultUser.getLastName());
        assertEquals("Sofy@mylove.com", resultUser.getEmail());
        assertEquals("112236", resultUser.getPhone());
    }

    @Test
    public void shouldFailReturnCurrentUserByUserId () {
        User user = new User();
        user.setFirstName("SOFY");
        user.setLastName("ASDY");
        user.setEmail("Sofy@mylove.com");
        user.setPhone("112236");

        Map<String, String> obj = null;

        ResponseEntity<User> response = userController.getOne(obj);

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verifyNoInteractions(userRepository);
    }

    @Test
    public void shouldSuccessReturnAllUser(){
        List<User> users = createUsers();

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        verify(userRepository).findAll();

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<User> results = response.getBody();
        assertNotNull(results);

        assertEquals( 2, results.size());
        assertEquals("Ahm", results.get(0).getFirstName());
        assertEquals("SOFY", results.get(1).getFirstName());
    }

    @Test
    public void shouldFailReturnAllUser() {
        when(userRepository.findAll()).thenThrow(new RuntimeException());
        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldSuccessCreateUser() {
        User user = new User();
        user.setFirstName("SOFY");
        user.setLastName("ASDY");
        user.setEmail("Sofy@mylove.com");
        user.setPhone("112236");

        User addedUser = new User();
        addedUser.setId(123L);
        addedUser.setFirstName("SOFY");
        addedUser.setLastName("ASDY");
        addedUser.setEmail("Sofy@mylove.com");
        addedUser.setPhone("112236");

        when(userRepository.save(user)).thenReturn(addedUser);
        ResponseEntity<Map<String, Object>> response = userController.updateUser(user);

        verify(userRepository).save(user);

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Map<String, Object> results = response.getBody();

        assertNotNull(results);
        assert(results.containsKey("Success") && results.get("Success").equals("Yes"));

        User resultUser = (User) results.get("data");

        assertNotEquals(user.getId(), resultUser.getId());
    }

    @Test
    public void shouldFailCreateUser() {
        User user = new User();
        user.setFirstName("SOFY");
        user.setLastName("ASDY");
        user.setEmail("Sofy@mylove.com");
        user.setPhone("112236");

        when(userRepository.save(user)).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> response = userController.updateUser(user);

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Map<String, Object> results = response.getBody();

        assertNotNull(results);
        assert(results.containsKey("Success") && results.get("Success").equals("No"));
    }

    @Test
    public void shouldSuccessUpdateUser(){
        User oldUser = new User();
        oldUser.setUserId(123L);
        oldUser.setFirstName("SOFY");
        oldUser.setLastName("ASDY");
        oldUser.setEmail("Sofy@mylove.com");
        //Old value of phone number
        oldUser.setPhone("727");

        User updatedUser = new User();
        updatedUser.setUserId(123L);
        updatedUser.setFirstName("SOFY");
        updatedUser.setLastName("ASDY");
        updatedUser.setEmail("Sofy@mylove.com");
        //New value of phone number
        updatedUser.setPhone("112236");

        when(userRepository.save(any (User.class))).thenReturn(updatedUser);


        ResponseEntity<Map<String, Object>> response = userController.updateUser(updatedUser);

        verify(userRepository).save(any (User.class));

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Map<String, Object> results = response.getBody();

        assertNotNull(results);
        assert(results.containsKey("Success") && results.containsKey("data"));
        assert(results.get("Success").equals("Yes"));

        User resultUser = (User) results.get("data");


        assertEquals(oldUser.getId(), resultUser.getId());
        assertEquals(updatedUser.getPhone(), resultUser.getPhone());
        assertNotEquals(oldUser.getPhone(), resultUser.getPhone());
    }

    @Test
    public void shouldFailUpdateUser(){
        User oldUser = new User();
        oldUser.setUserId(123L);
        oldUser.setFirstName("SOFY");
        oldUser.setLastName("ASDY");
        oldUser.setEmail("Sofy@mylove.com");
        //Old value of phone number
        oldUser.setPhone("727");

        User updatedUser = new User();
        updatedUser.setUserId(123L);
        updatedUser.setFirstName("SOFY");
        updatedUser.setLastName("ASDY");
        updatedUser.setEmail("Sofy@mylove.com");
        //New value of phone number
        updatedUser.setPhone("112236");

        when(userRepository.save(any (User.class))).thenThrow(new RuntimeException());


        ResponseEntity<Map<String, Object>> response = userController.updateUser(updatedUser);

        verify(userRepository).save(any (User.class));

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        Map<String, Object> results = response.getBody();

        assertNotNull(results);
        assert(results.containsKey("Success") && !results.containsKey("data"));
        assert(results.get("Success").equals("No"));
    }



    public List<User> createUsers(){
        List<User> users = new ArrayList<>();

        //Creating first user
        User user1 = new User();
        user1.setFirstName("Ahm");
        user1.setLastName("Hnt");
        user1.setPhone("111");
        user1.setEmail("itsme@home.com");
        user1.setUserId(123L);

        //Adding first user
        users.add(user1);


        //Creating second user
        User user2 = new User();
        user2.setFirstName("SOFY");
        user2.setLastName("Hnt");
        user2.setPhone("222");
        user2.setEmail("itsme2@home.com");
        user2.setUserId(124L);

        //Adding second user
        users.add(user2);

        return users;
    }

}
