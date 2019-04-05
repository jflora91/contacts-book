package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.model.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {


    /**
     * When invoking this endpoint receive a list of contacts.
     *
     * @return a list of contacts
     */
    @GetMapping("/contacts")
    public List<Contact> getAllAvailableContacts () {
        return null;
    }

    /**
     * When invoking this endpoint, provide a phone number and receive a list of contacts matching the phone number.
     * The result is a list because more than one user can have the same contact with different names.
     *
     * @param phoneNumber
     * @return a list of contacts with that phone number
     */
//    @GetMapping("/contacts")
//    public List<Contact> getContactsMatchPhoneNumber(@Valid @RequestBody Long phoneNumber) {
//        //return contactRepository.findByPhoneNumber(phoneNumber);
//        return null;
//    }
    //TODO this method will search by phonenumber but path need to be different









}
