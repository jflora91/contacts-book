package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mindera.graduate.contactsbook.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    /**
     * An entity when invoking this receive a list of registered users on the service.
     * It should be possible to list users by registration date, update date, First and last name.
     *
     * @return a list of users
     */
    @GetMapping("/user")
    public List<User> getAllUsers () {
        return null;
    }

    /**
     * An entity when invoking this endpoint provide the user identification
     *  and receive a list of contacts that are associated with that user.
     *
     * @param userId
     * @return a list of contacts
     */
    @GetMapping("/user/{userI}/contacts")
    public List<Contact> getAllUserContacts(@PathVariable Long userId){
        return null;
    }


    /**
     * An entity when invoking this endpoint provide the new user information
     *  and receive in return the newly created user.
     *
     * @param user
     * @return if user well saved
     */
    @PostMapping("/user")
    public @Valid User addUser(@Valid @RequestBody User user){ // @Valid trigger validation for this field

        //fixme All users have also a contact, the service should be able to provide that information

        return null;
    }


    /**
     * An entity when invoking this endpoint provide the user identification and the complete user information,
     *  in result it will receive the updated user.
     * @param userId
     * @param user
     * @return all information of the updated user
     */
    @PutMapping("/user/{userId}")
    public User updateUser(@Valid Long userId, @Valid @RequestBody User user){
        return user;
        // userRepository.findById(userId);
                //fixme continue, after find the user update info with user in body
    }




}
