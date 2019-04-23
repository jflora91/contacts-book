package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.ContactsBookApplication;
import com.mindera.graduate.contactsbook.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ContactsBookApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    UserController userController;
    @Test
    public void addUser() {
        UserDTO userDTO = new UserDTO(1L, "john", "michael");
        userDTO.setId(null);
        UserDTO userDTO1 = userController.addUser(userDTO);
        assertNotNull(userDTO1);
        assertNotNull(userDTO1.getId());
        assertEquals(userDTO.getFirstName(), userDTO1.getFirstName());
        assertEquals(userDTO.getLastName(), userDTO1.getLastName());
    }

    @Test
    public void createAndGet(){
        UserDTO userDTO = new UserDTO(1L, "maria", "chris");
        userDTO.setId(null);
        UserDTO userDTOAdded = userController.addUser(userDTO);
        assertNotNull(userDTOAdded);
        assertNotNull(userDTOAdded.getId());
        UserDTO userDTOGetted = userController.getUser(userDTOAdded.getId());
        assertNotNull(userDTOGetted);
        assertNotNull(userDTOGetted.getId());
        assertEquals(userDTOAdded.getFirstName(), userDTOGetted.getFirstName());
        assertEquals(userDTOAdded.getLastName(), userDTOGetted.getLastName());
    }

}