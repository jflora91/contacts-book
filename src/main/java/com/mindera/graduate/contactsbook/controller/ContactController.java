package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ContactController {


    @Autowired
    private ContactService contactService;

    @Autowired
    private HttpServletRequest request;

    /**
     * When invoking this endpoint receive a list of contacts.
     * OR
     * When invoking this endpoint, provide a phone number and receive a list of contacts matching the phone number.
     * The result is a list because more than one user can have the same contact with different names.
     *
     * @param phoneNumber
     * @return a list of contacts with that phone number
     */
    @GetMapping("/contacts")
    public List<ContactDTO> getContactsMatchPhoneNumber(@RequestParam(required = false) String phoneNumber) {
        if (phoneNumber != null) {
            return contactService.findByPhoneNumber(phoneNumber);
        }
        return contactService.getAllContacts();
    }

    @GetMapping("/contacts/{contactId}")
    public ContactDTO getContact(@PathVariable Long contactId) {
        return contactService.getContact(contactId);
    }

}
