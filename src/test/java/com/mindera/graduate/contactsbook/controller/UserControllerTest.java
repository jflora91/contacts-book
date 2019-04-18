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
        UserDTO userDTO = new UserDTO(1L, "adeus", "oa");
        userDTO.setId(null);
        UserDTO userDTO1 = userController.addUser(userDTO);
        assertNotNull(userDTO1);
        assertNotNull(userDTO1.getId());
        assertEquals(userDTO.getFirstName(), userDTO1.getFirstName());
        assertEquals(userDTO.getLastName(), userDTO1.getLastName());
    }

    @Test
    public void createAndGet(){
//        UserDTO userDTO = new UserDTO(1L, "adeus", "oa");
//        userDTO.setId(null);
//        UserDTO userDTO1 = userController.addUser(userDTO);
//        assertNotNull(userDTO1);
//        assertNotNull(userDTO1.getId());
//        UserDTO userDTO2 = userController.getUser(userDTO1.getId());
//        assertNotNull(userDTO2);
//        assertNotNull(userDTO2.getId());
//        assertEquals(userDTO1.getFirstName(), userDTO2.getFirstName());
//        assertEquals(userDTO1.getLastName(), userDTO2.getLastName());
    }

}