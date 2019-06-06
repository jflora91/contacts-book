package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.ContactsBookApplication;
import com.mindera.graduate.contactsbook.controller.UserController;
import com.mindera.graduate.contactsbook.dto.ContactDTO;
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
import java.util.Collections;
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
    private UserController userController;

    @Test
    public void updateUser() {
        List<String> phoneNumbers = List.of("112", "113", "114");

        UserDTO userDTO = new UserDTO(null, "elton", "john", phoneNumbers);

        UserDTO userDTOSaved = userController.addUser(userDTO);
        assertNotNull(userDTOSaved);

        List<String> phoneNumbersNew = List.of("223", "113", "224");

        UserDTO userDTOToUpdate = new UserDTO(userDTOSaved.getId(), "james", "anderson", phoneNumbersNew);

        // update firstName, lastName and 2 phone numbers
        UserDTO userDTOUpdated = userService.updateUser(userDTOSaved.getId(), userDTOToUpdate);
        logger.info("firstName update:{}->{}, lastName update:{}->{}, phoneNumbers update:{}->{}",
                userDTOSaved.getFirstName(), userDTOToUpdate.getFirstName(),
                userDTOSaved.getLastName(), userDTOToUpdate.getLastName(),
                userDTOSaved.getPhoneNumbers(), userDTOToUpdate.getPhoneNumbers());

        assertNotNull(userDTOUpdated);

        // compare id's
        assertEquals(userDTOUpdated.getId(), userDTOSaved.getId());
        assertEquals(userDTOUpdated.getId(), userDTOToUpdate.getId());
        logger.info("Compare if id's equal: " + userDTOUpdated.getId());

        // compare userDTOUpdated with userDTO
        assertNotEquals(userDTOUpdated.getPhoneNumbers(), userDTO.getPhoneNumbers());
        assertNotEquals(userDTOUpdated.getFirstName(), userDTO.getFirstName());
        assertNotEquals(userDTOUpdated.getLastName(), userDTO.getLastName());

        // compare userDTOToUpdate with userDTOUpdated
//        Collections.sort(userDTOUpdated.getPhoneNumbers());
//        Collections.sort(userDTOToUpdate.getPhoneNumbers());

        ArrayList userDTOUpdatedPhoneNumbers = new ArrayList();
        userDTOUpdatedPhoneNumbers.addAll(userDTOUpdated.getPhoneNumbers());
        Collections.sort(userDTOUpdatedPhoneNumbers);

        ArrayList userDTOToUpdatePhoneNumbers = new ArrayList();
        userDTOToUpdatePhoneNumbers.addAll(userDTOToUpdate.getPhoneNumbers());
        Collections.sort(userDTOToUpdatePhoneNumbers);

        assertEquals(userDTOUpdatedPhoneNumbers, userDTOToUpdatePhoneNumbers);
        assertEquals(userDTOToUpdate.getFirstName(), userDTOUpdated.getFirstName());
        assertEquals(userDTOToUpdate.getLastName(), userDTOUpdated.getLastName());

        UserDTO userDTOWithoutContact = new UserDTO(userDTOSaved.getId(), "bach", "trump", null);
        // update firstName, lastName. userDTOSaved had a contact, after update don't have anymore
        UserDTO userDTOWithoutContactUpdated = userService.updateUser(userDTOSaved.getId(), userDTOWithoutContact);
        assertTrue(userDTOWithoutContactUpdated.getPhoneNumbers().isEmpty());
        logger.info("Update without own contact delete the old own contact of the user to update\n {}->{}", userDTOSaved.getPhoneNumbers(), userDTOWithoutContactUpdated.getPhoneNumbers());

    }

    //todo not finished yet i guess @Test
    public void updateContact() {

        UserDTO userDTO = new UserDTO(null, "elton", "john");
        UserDTO userDTOSaved = userController.addUser(userDTO);
        assertNotNull(userDTOSaved);
        logger.info("User added: id: {}, first name: {}, last name: {}"
                , userDTOSaved.getId(), userDTOSaved.getFirstName(), userDTOSaved.getLastName());

        List<String> phoneNumbers = List.of("113", "112", "111");

        List<String> phoneNumbersNew = new ArrayList<>();
        phoneNumbersNew.add("223");
        phoneNumbersNew.add("113");
        phoneNumbersNew.add("224");

        ContactDTO contactDTO = new ContactDTO(null, "george", "obama", phoneNumbers);
        ContactDTO contactDTOSaved = userController.addUserContact(userDTOSaved.getId(), contactDTO);
        assertNotNull(contactDTOSaved);
        logger.info("Contact added: id: {}, first name: {}, last name: {}, phone number: {}",
                contactDTOSaved.getId(), contactDTOSaved.getFirstName(),
                contactDTOSaved.getLastName(), contactDTOSaved.getPhoneNumbers());

        ContactDTO contactDTOUpdate =
                new ContactDTO(contactDTOSaved.getId(), "micael", "ornald", phoneNumbersNew);

        ContactDTO contactDTOUpdated = userService.updateContact(userDTOSaved.getId(), contactDTOSaved.getId(), contactDTOUpdate);
        logger.info("Update contact with: first name:{}, last name:{}, phone numbers: {}",
                contactDTOUpdate.getFirstName(), contactDTOUpdate.getLastName(), contactDTOUpdate.getPhoneNumbers());

        // check that names are different before and after update
        assertNotEquals(contactDTOSaved.getFirstName(), contactDTOUpdated.getFirstName());
        assertNotEquals(contactDTOSaved.getLastName(), contactDTOUpdated.getLastName());

        assertTrue(contactDTOUpdate.getPhoneNumbers().containsAll(contactDTOUpdated.getPhoneNumbers()));

        assertTrue(contactDTOUpdate.getPhoneNumbers().containsAll(contactDTOUpdated.getPhoneNumbers()));

        // check that the list of phone numbers are different before and after update
        Collections.sort(contactDTOUpdated.getPhoneNumbers());
        Collections.sort(contactDTOSaved.getPhoneNumbers());
        assertNotEquals(contactDTOSaved.getPhoneNumbers(), contactDTOUpdated.getPhoneNumbers());

        // TEST

        List<Long> lista1 = List.of(1L, 2L, 3L, 4L, 5L);
        List<Long> lista2 = List.of(3L, 2L, 1L, 4L);
        // contains, can have more or different
        assertTrue(lista1.containsAll(lista2));
        //assertEquals(lista1,lista2);

        // END TEST

        // TEST
//todo check what is going here

//        List<Long> lista1 = List.of(1L,2L,3L,4L,5L);
//        List<Long> lista2 = List.of(3L,2L,1L,4L);
        // contains, can have more or different
        assertTrue(lista1.containsAll(lista2));
        //assertEquals(lista1,lista2);

        // END TEST

        // compare names before and after update
        assertEquals(contactDTOUpdate.getFirstName(), contactDTOUpdated.getFirstName());
        assertEquals(contactDTOUpdate.getLastName(), contactDTOUpdated.getLastName());

        // check if phone numbers list are equal after update

        Collections.sort(contactDTOUpdate.getPhoneNumbers());
        assertEquals(contactDTOUpdate.getPhoneNumbers(), contactDTOUpdated.getPhoneNumbers());

        logger.info("Contact after update: id:{}, first name: {}, last name:{}, phone numbers:{}",
                contactDTOUpdated.getId(), contactDTOUpdated.getFirstName(), contactDTOUpdated.getLastName(),
                contactDTOUpdated.getPhoneNumbers());

    }
}