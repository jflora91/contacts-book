package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.ContactsBookApplication;
import com.mindera.graduate.contactsbook.controller.UserController;
import com.mindera.graduate.contactsbook.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ContactsBookApplication.class)
@AutoConfigureMockMvc
public class UserServiceTest {

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

        UserDTO userDTO = new UserDTO(null, "farinha", "flora", phoneNumbers);

        UserDTO userDTOSaved = userController.addUser(userDTO);
        assertNotNull(userDTOSaved);

        List<String> phoneNumbersNew = new ArrayList();
        phoneNumbers.add("223");
        phoneNumbers.add("113");
        phoneNumbers.add("224");

        UserDTO userDTOUpdate = new UserDTO(userDTOSaved.getId(), "joao", "francisco", phoneNumbersNew);
        UserDTO userDTOUpdated = userService.updateUser(userDTOSaved.getId(), userDTOUpdate);

        assertNotNull(userDTOUpdated);

        assertEquals(userDTOUpdated.getId(), userDTOSaved.getId());

        assertEquals(userDTOUpdate.getPhoneNumbers(), userDTOUpdated.getPhoneNumbers());
        assertEquals(userDTOUpdate.getFirstName(), userDTOUpdated.getFirstName());
        assertEquals(userDTOUpdate.getLastName(), userDTOUpdated.getLastName());

    }
}
