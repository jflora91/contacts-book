package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;

public interface IContactService{
    ContactDTO addContact(Long userId, ContactDTO contactDTO);
}
