package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.dto.UserDTO;

public interface IUserService {
    UserDTO addUser(UserDTO userDTO);
    ContactDTO updateContact(Long userId, Long contactId, ContactDTO contactDTO);

    UserDTO updateUser(Long userId, UserDTO userDTO);
}
