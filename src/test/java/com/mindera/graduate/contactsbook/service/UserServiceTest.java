package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.ContactsBookApplication;
import com.mindera.graduate.contactsbook.controller.UserController;
import com.mindera.graduate.contactsbook.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ContactsBookApplication.class)
@AutoConfigureMockMvc
public class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    UserController userController;

    @Test
    public void updateUser() {
        List<String> phoneNumbers = new ArrayList();
        phoneNumbers.add("112");
        phoneNumbers.add("113");
        phoneNumbers.add("114");

        UserDTO userDTO = new UserDTO(null, "elton", "john", phoneNumbers);

        UserDTO userDTOSaved = userController.addUser(userDTO);
        assertNotNull(userDTOSaved);

        List<String> phoneNumbersNew = new ArrayList();
        phoneNumbers.add("223");
        phoneNumbers.add("113");
        phoneNumbers.add("224");

        UserDTO userDTOToUpdate = new UserDTO(userDTOSaved.getId(), "james", "anderson", phoneNumbersNew);

        // update firstName, lastName and 2 phone numbers
        UserDTO userDTOUpdated = userService.updateUser(userDTOSaved.getId(), userDTOToUpdate);

        assertNotNull(userDTOUpdated);

        // compare id's
        assertEquals(userDTOUpdated.getId(), userDTOSaved.getId());
        assertEquals(userDTOUpdated.getId(), userDTOToUpdate.getId());

        // compare userDTOUpdated with userDTO
        assertNotEquals(userDTOUpdated.getPhoneNumbers(), userDTO.getPhoneNumbers());
        assertNotEquals(userDTOUpdated.getFirstName(), userDTO.getFirstName());
        assertNotEquals(userDTOUpdated.getLastName(), userDTO.getLastName());

        // compare userDTOToUpdate with userDTOUpdated
        assertEquals(userDTOToUpdate.getPhoneNumbers(), userDTOUpdated.getPhoneNumbers());
        assertEquals(userDTOToUpdate.getFirstName(), userDTOUpdated.getFirstName());
        assertEquals(userDTOToUpdate.getLastName(), userDTOUpdated.getLastName());


        UserDTO userDTOWithoutContact = new UserDTO(userDTOSaved.getId(), "bach", "trump", null);
        // update firstName, lastName. userDTOSaved had a contact, after update don't have anymore
        UserDTO userDTOWithoutContactUpdated = userService.updateUser(userDTOSaved.getId(), userDTOWithoutContact);
        assertTrue(userDTOWithoutContactUpdated.getPhoneNumbers().isEmpty());

    }
}