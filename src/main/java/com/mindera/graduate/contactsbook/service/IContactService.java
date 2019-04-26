package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;

import java.util.List;

public interface IContactService{
    ContactDTO addContact(Long userId, ContactDTO contactDTO);
    List<ContactDTO> findUserContacts(Long userId);
    List<ContactDTO> getAllContacts();
    List<ContactDTO> findByPhoneNumber(String phoneNumber);
    ContactDTO getContact(Long contactId);

    }
