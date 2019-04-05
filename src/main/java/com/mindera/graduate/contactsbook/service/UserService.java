package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.ContactNumber;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.repository.ContactNumberRepository;
import com.mindera.graduate.contactsbook.repository.ContactRepository;
import com.mindera.graduate.contactsbook.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactNumberRepository contactNumberRepository;
    /**
     * MapperFactory use to configure mappings
     * MapperFacade obtained from MapperFactory which performs the actual mapping work
     */
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    /**
     * if the user comes with a contact we need to add the contact too
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO addUser(UserDTO userDTO) {

        User user = convertToEntity(userDTO);
        if (userDTO.getPhoneNumbers() != null && !userDTO.getPhoneNumbers().isEmpty()) {  // if we add a user with own contact
            Contact contact = new Contact(user.getFirstName(), user.getLastName(), user);
            contactRepository.save(contact);
            for (String phoneNumber : userDTO.getPhoneNumbers()) {
                ContactNumber contactNumber = new ContactNumber(phoneNumber, contact); // add the phone numbers for this contact
                contactNumberRepository.save(contactNumber);
            }
            user.setOwnContact(contact);
        }
        return convertToDTO(userRepository.save(user));
    }

    // arrayList: Use when work with fix amount of objects and there is a frequent get of elements.

    /**
     * get all users and (if exist) all is phone numbers
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
            allUsersDTO.add(convertToDTO(user));
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                allUsersDTO.get(allUsersDTO.indexOf(user)).setPhoneNumbers(phoneNumbers);
            }
        }
        return allUsersDTO;
    }

    /**
     * receive a dto and convert to a entity representing the DB
     *
     * @param userDTO
     * @return
     */
    private User convertToEntity(UserDTO userDTO) {
        mapperFactory.classMap(UserDTO.class, User.class)
                .exclude("phoneNumbers");
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(userDTO, User.class);
    }

    /**
     * receive a entity and convert to a DTObject
     *
     * @param user
     * @return
     */
    private UserDTO convertToDTO(User user) {
        mapperFactory.classMap(User.class, UserDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return userDTO;
    }


}
