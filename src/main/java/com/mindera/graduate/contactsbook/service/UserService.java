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
            Contact contact = contactRepository.save(new Contact(user.getFirstName(), user.getLastName(), user));
            for (String phoneNumber : userDTO.getPhoneNumbers())
            {
                ContactNumber contactNumber = new ContactNumber(phoneNumber, contact); // add the phone numbers for this contact
                contactNumberRepository.save(contactNumber);
            }
            user.setOwnContact(contact);
            user = userRepository.save(user);

        }
        return mapperConvert.convertToUserDTO(user);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + userDTO.getId() + " doesn't exist"));

        if (userId != userDTO.getId()) {
            logger.error("User ID: {} in url is not equal to ID: {} in the object in body request", userId, userDTO.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID incoherence");
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
                ContactNumber contactNumber = contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, userToUpdate.getOwnContact());
                // check if phone number already exist agregated to this contact, if not create a new one
                if ( contactNumber == null) {
                    contactNumberRepository.save(new ContactNumber(phoneNumber, userToUpdate.getOwnContact()));
                }

            });
            // remove old phone numbers
            List<String> phoneNumbers = getPhoneNumbersFromOwnContact(userToUpdate.getOwnContact());
            phoneNumbers.removeAll(userDTO.getPhoneNumbers()); // just have the phone numbers to delete
            phoneNumbers.forEach(phoneNumber -> contactNumberRepository.delete(contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, userToUpdate.getOwnContact())));
        }

        // update remaining information
        User userUpdates = mapperConvert.convertToUser(userDTO);
        userToUpdate.setFirstName(userUpdates.getFirstName());
        userToUpdate.setLastName(userUpdates.getLastName());

        UserDTO userDTOToReturn = mapperConvert.convertToUserDTO(userRepository.save(userToUpdate));
        userDTOToReturn.setPhoneNumbers(getPhoneNumbersFromOwnContact(userToUpdate.getOwnContact()));

        return userDTOToReturn;
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
            UserDTO userDTO = mapperConvert.convertToUserDTO(user);
            userDTO.setPhoneNumbers(getPhoneNumbersFromOwnContact(user.getOwnContact()));
            allUsersDTO.add(userDTO);

        }
        return allUsersDTO;
    }


    /**
     * get a user by ID
     * return that user
     * @param userId
     * @return
     */
    public UserDTO getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID:" + userId + " doesn't exist"));

        UserDTO userDTO = mapperConvert.convertToUserDTO(user);
        userDTO.setPhoneNumbers(getPhoneNumbersFromOwnContact(user.getOwnContact()));
        return userDTO;
    }

    /**
     * receive the user contact and return a list of phone numbers of that user
     * @param ownContact
     * @return
     */
    private List<String> getPhoneNumbersFromOwnContact(Contact ownContact) {
        List<String> phoneNumbers = new ArrayList<>();

        if (ownContact != null) {
            Contact contact = ownContact;
            List<ContactNumber> allContactNumbers = contactNumberRepository.findByContact(contact);
            allContactNumbers.forEach(contactNumber -> phoneNumbers.add(contactNumber.getPhoneNumber()));
        }

        return phoneNumbers;
    }

}
