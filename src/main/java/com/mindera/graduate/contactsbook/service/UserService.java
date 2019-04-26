package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
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

    @Autowired
    private ContactService contactService;

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
                if (!ContactService.isValid(phoneNumber)) { // check if phone numbers are valid
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '" + phoneNumber + "' is not valid");
                }
                ContactNumber contactNumber = new ContactNumber(phoneNumber, contact); // add the phone numbers for this contact
                contactNumberRepository.save(contactNumber);
            }
            user.setOwnContact(contact);
            user = userRepository.save(user);

        }
        UserDTO userDTOWithPhoneNumbers = mapperConvert.convertToUserDTO(user);
        userDTOWithPhoneNumbers.setPhoneNumbers(contactService.getPhoneNumbersFromContact(user.getOwnContact()));
        return userDTOWithPhoneNumbers;
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + userDTO.getId() + " doesn't exist"));

        if (userId != userDTO.getId()) {
            logger.error("User ID: {} in url is not equal to ID: {} in the object in body request", userId, userDTO.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID incoherence");
        }

        if (userDTO.getPhoneNumbers() == null) {
            cleanOwnContact(userToUpdate);  // if there is no phone numbers, dont create a own contact or delete it
        }
        else {
            updateUserOwnContact(userToUpdate, userDTO);    // update own contact of user (3 states)
        }

        User userUpdated = updateFirstLastName(userDTO, userToUpdate);  // update first and last name of user

        UserDTO userDTOToReturn = mapperConvert.convertToUserDTO(userRepository.save(userUpdated));

        userDTOToReturn.setPhoneNumbers(contactService.getPhoneNumbersFromContact(userUpdated.getOwnContact()));

        return userDTOToReturn;
    }

    /**
     * don't create a new contact or/and delete the contact existed
     * @param userToUpdate
     */
    private void cleanOwnContact(User userToUpdate) {

        if (userToUpdate.getOwnContact() != null) {
            logger.info("Delete user contact, there is no phone number in update");
            userToUpdate.setOwnContact(null);
        }
    }

    /**
     * Update user own contact, update the phone numbers related with user contact
     *
     * 1- add the new phone number
     * 2- delete the ones that doesn't come in the update
     * 3- don't touch in the ones already in the own contact and that come in update
     *
     * @param userToUpdate
     * @param userDTO
     */
    private void updateUserOwnContact(User userToUpdate, UserDTO userDTO) {
        // create contact of user if doesn't exist
        if (userToUpdate.getOwnContact() == null) {
            logger.info("Create new contact of user, because there is phone numbers in update");
            Contact contactNew = new Contact(userDTO.getFirstName(), userDTO.getLastName(), userToUpdate);
            userToUpdate.setOwnContact(contactRepository.save(contactNew));
        }

        userDTO.getPhoneNumbers().forEach(phoneNumber -> {
            if (!ContactService.isValid(phoneNumber)) { // check if phone numbers are valid
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '" + phoneNumber + "' is not valid");
            }
            ContactNumber contactNumber = contactNumberRepository.findByPhoneNumberAndContact(
                    phoneNumber, userToUpdate.getOwnContact());
            // check if phone number already exist agregated to this contact, if not create a new one
            if ( contactNumber == null) {
                logger.info("Phone number {} added to user contact", phoneNumber);
                contactNumberRepository.save(new ContactNumber(phoneNumber, userToUpdate.getOwnContact()));
            }

        });
        // remove old phone numbers
        List<String> phoneNumbers = contactService.getPhoneNumbersFromContact(userToUpdate.getOwnContact());
        phoneNumbers.removeAll(userDTO.getPhoneNumbers()); // just have the phone numbers to delete
        phoneNumbers.forEach(phoneNumber -> {
            logger.info("Remove phone number {}, it's a old contact and doesnt come in the new update", phoneNumber);
            contactNumberRepository.delete(
                    contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, userToUpdate.getOwnContact()));
        });
    }

    /**
     * update remaining information, first and last name
     * @param userDTO
     * @param userToUpdate
     * @return
     */
    private User updateFirstLastName(UserDTO userDTO, User userToUpdate) {
        User userUpdates = mapperConvert.convertToUser(userDTO);
        userToUpdate.setFirstName(userUpdates.getFirstName());
        userToUpdate.setLastName(userUpdates.getLastName());
        return userToUpdate;
    }


    /**
     * get all users and (if exist) all is phone numbers
     * @return
     */
    public List<UserDTO> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUsersDTO = new ArrayList<>();

        for (User user: allUsers) {
            UserDTO userDTO = mapperConvert.convertToUserDTO(user);
            userDTO.setPhoneNumbers(contactService.getPhoneNumbersFromContact(user.getOwnContact()));
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
        userDTO.setPhoneNumbers(contactService.getPhoneNumbersFromContact(user.getOwnContact()));
        return userDTO;
    }


    /**
     * receive a user id (owner of this contact), id of the contact to be updated and receive the contact with
     *  the information to be updated
     * check all the validation to make the contact update
     * return the contact after the update
     * @param userId
     * @param contactId
     * @param contactDTO
     * @return
     */
    @Override
    public ContactDTO updateContact(Long userId, Long contactId, ContactDTO contactDTO){

        checkUserExistence(userId); // check if user exist

        Contact contactToUpdate = checkContactExistence(contactId); // check if contact exist

        checkIdEquality(userId, contactToUpdate.getUser().getId()); // check coherence of id's in url and body request

        checkIdEquality(contactId, contactDTO.getId()); // check coherence of id's in url and body request

        checkPhoneNumbersExistance(contactDTO.getPhoneNumbers()); // contact need to have one or more phone numbers

        contactDTO = updateContactPhoneNumbers(contactDTO, contactToUpdate);    // update the phone numbers of a contact

        Contact contactUpdates = mapperConvert.convertToContact(contactDTO);
        // update first name of contact if need it
        if (contactToUpdate.getFirstName() != contactUpdates.getFirstName()) {
            contactToUpdate.setFirstName(contactUpdates.getFirstName());
        }

        // update last name of contact if need it
        if (contactToUpdate.getLastName() != contactUpdates.getLastName()) {
            contactToUpdate.setLastName(contactUpdates.getLastName());
        }

        Contact contactUpdated = contactRepository.save(contactToUpdate);
        ContactDTO contactDTOUpdated = mapperConvert.convertToContactDTO(contactUpdated);
        contactDTOUpdated.setPhoneNumbers(contactService.getPhoneNumbersFromContact(contactUpdated));
        return contactDTOUpdated;
    }

    /**
     * Check if there is phone numbers in the list
     * @param phoneNumbers
     */
    private void checkPhoneNumbersExistance(List<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            logger.error("Contact need to have one or more phone numbers");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact need to have one or more phone numbers");
        }
    }

    /**
     * receive two Long elements and check if they are not equal
     * it's used to compare id's of users or contacts
     * @param id1
     * @param id2
     */
    private void checkIdEquality(Long id1, Long id2) {
        if (id1 != id2) {
            logger.error("ID(user or contact): {} in url is not equal to ID(user or contact): {} of in body request", id1, id2);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "(user or contact) ID in url not correspond to (user or contact) ID in contact to update");
        }
    }

    /**
     * check if contact exist by the id
     * @param contactId
     * @return
     */
    private Contact checkContactExistence(Long contactId) {
        return contactRepository.findById(contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact with ID: " + contactId + " doesn't exist"));

    }

    /**
     * check if user exist by the id
     * @param userId
     * @return
     */
    private User checkUserExistence(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + userId + " doesn't exist"));

    }

    /**
     * update the phone numbers of a contact:
     * - add new phone numbers
     * - delete the old ones and that doesn't come in the update
     * -
     * @param contactDTO
     * @param contactToUpdate
     * @return
     */
    private ContactDTO updateContactPhoneNumbers(ContactDTO contactDTO, Contact contactToUpdate) {
        contactDTO.getPhoneNumbers().forEach(phoneNumber -> {
            ContactNumber contactNumber = contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, contactToUpdate);
            if (contactNumber == null) {
                if (!ContactService.isValid(phoneNumber)) { // check if phone numbers are valid
                    logger.error("Contact number:{} is not valid", phoneNumber);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '" + phoneNumber + "' is not valid");
                }
                contactNumberRepository.save(new ContactNumber(phoneNumber, contactToUpdate));
                logger.info("Phone number {} added to user contact", phoneNumber);
            }
        });

        // remove old phone numbers
        List<String> phoneNumbers = contactService.getPhoneNumbersFromContact(contactToUpdate);
        phoneNumbers.removeAll(contactDTO.getPhoneNumbers()); // just have the phone numbers to delete

        phoneNumbers.forEach(phoneNumber -> {
            logger.info("Remove phone number {}, it's a old contact and doesnt come in the new update", phoneNumber);
            contactNumberRepository.delete(contactNumberRepository.findByPhoneNumberAndContact(phoneNumber, contactToUpdate));
        });
        return contactDTO;
    }

}
