package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    /**
     * When invoking this endpoint receive a list of contacts.
     *
     * @return a list of contacts
     */
    @GetMapping("/contacts")
    public List<ContactDTO> getAllAvailableContacts() {

        return contactService.getAllContacts();

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
