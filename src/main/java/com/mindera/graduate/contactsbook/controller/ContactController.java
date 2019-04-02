package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mindera.graduate.contactsbook.repository.ContactRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController {

    /**
     * An entity when invoking this endpoint receive a list of contacts.
     *
     * @return a list of contacts
     */
    @GetMapping("/contact")
    public List<Contact> getAllAvailableContacts () {
        return null;
    }

    /**
     * An entity when invoking this endpoint, provide a phone number and receive a list of contacts matching the phone number.
     * The result is a list because more than one user can have the same contact with different names.
     *
     * @param phoneNumber
     * @return a list of contacts with that phone number
     */
    @GetMapping("/contact/{phoneNumber}")
    public List<Contact> getContactsMatchPhoneNumber(@PathVariable Long phoneNumber) {
        return null;
    }


}
