package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.mapper.MapperConvert;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.ContactNumber;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.repository.ContactNumberRepository;
import com.mindera.graduate.contactsbook.repository.ContactRepository;
import com.mindera.graduate.contactsbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    private MapperConvert mapperConvert = new MapperConvert();

    /**
     * if the user comes with a contact we need to add the contact too
     *
     * @param userDTO
     * @return
     */

    public UserDTO addUser(UserDTO userDTO) {

        User user = mapperConvert.convertToUser(userDTO);
        user = userRepository.save(user);

        if (userDTO.getPhoneNumbers() != null && !userDTO.getPhoneNumbers().isEmpty())   // if we add a user with own contact
        {
            Contact contact = new Contact(user.getFirstName(), user.getLastName(), user);
            contactRepository.save(contact);
            for (String phoneNumber : userDTO.getPhoneNumbers())
            {
                ContactNumber contactNumber = new ContactNumber(phoneNumber, contact); // add the phone numbers for this contact
                contactNumberRepository.save(contactNumber);
            }
            user.setOwnContact(contact);
        }
        return mapperConvert.convertToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + userDTO.getId() + " doesn't exist"));

        if (userId != userDTO.getId()) {
            logger.error("User ID: {} in url is not equal to ID: {} in the object in body request", userId, userDTO.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID incoherence");
            //todo make test
        }

        // if there is no phone numbers, dont create new contact OR delete the contact existed
        if (userDTO.getPhoneNumbers() == null) {
            if (userToUpdate.getOwnContact() != null) {
                userToUpdate.setOwnContact(null);
            }
        }
        else {
            // create contact of user if doesn't exist
            if (userToUpdate.getOwnContact() == null) {
                Contact contactNew = new Contact(userDTO.getFirstName(), userDTO.getLastName(), userToUpdate);
                userToUpdate.setOwnContact(contactRepository.save(contactNew));
            }

            userDTO.getPhoneNumbers().forEach(phoneNumber -> {
                // check if phone number already exist agregated to this contact
                if (contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, userToUpdate.getOwnContact()) == null) {
                    contactNumberRepository.save(new ContactNumber(phoneNumber, userToUpdate.getOwnContact()));
                }
            });

        }

        // update remaining information
        User userUpdates = mapperConvert.convertToUser(userDTO);
        userToUpdate.setFirstName(userUpdates.getFirstName());
        userToUpdate.setLastName(userUpdates.getLastName());

        return mapperConvert.convertToUserDTO(userRepository.save(userToUpdate));
    }


    /**
     * get all users and (if exist) all is phone numbers
     * arrayList: Use when work with fix amount of objects and there is a frequent get of elements.
     * @return
     */
    public List<UserDTO> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUsersDTO = new ArrayList<>();

        for (User user: allUsers) {
            List<String> phoneNumbers = new ArrayList<>();
            if (user.getOwnContact() != null) {
                Contact contact = user.getOwnContact();
                List<ContactNumber> allContactNumbers = contactNumberRepository.findByContact(contact);

                allContactNumbers.forEach(x -> phoneNumbers.add(x.getPhoneNumber()));
            }
            allUsersDTO.add(mapperConvert.convertToUserDTO(user));
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                allUsersDTO.get(allUsersDTO.indexOf(user)).setPhoneNumbers(phoneNumbers);
            }
        }
        return allUsersDTO;
    }



}
