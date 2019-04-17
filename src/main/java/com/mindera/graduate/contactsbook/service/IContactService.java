package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.dto.UserContactsDTO;

import java.util.List;

public interface IContactService{
    ContactDTO addContact(Long userId, ContactDTO contactDTO);
    UserContactsDTO findUserContacts(Long userId);
    List<ContactDTO> getAllContacts();
    List<ContactDTO> findByPhoneNumber(String phoneNumber);
}
