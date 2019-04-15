package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {


    @Autowired
    private ContactService contactService;

    /**
     * When invoking this endpoint receive a list of contacts.
     *
     * @return a list of contacts
     */
    @GetMapping("/contact")
    public List<Contact> getAllAvailableContacts () {
        //todo: query parameter its a possibility of the phone number getting here
        return null;
    }

    /**
     * When invoking this endpoint, provide a phone number and receive a list of contacts matching the phone number.
     * The result is a list because more than one user can have the same contact with different names.
     *
     * @param phoneNumber
     * @return a list of contacts with that phone number
     */
    @GetMapping("/contacts")
    public List<ContactDTO> getContactsMatchPhoneNumber(@RequestParam String phoneNumber) {
        return contactService.findByPhoneNumber(phoneNumber);
    }

}
