package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * When invoking this endpoint provide the new user information
     *  and receive in return the newly created user.
     *
     * @param 
     * @return if user well saved
     */
    @RequestMapping(method = RequestMethod.POST)
    @PostMapping("/users")
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }


    /**
     * When invoking this receive a list of registered users on the service.
     * It should be possible to list users by registration date, update date, First and last name.
     *
     * @return a list of users
     */
    @GetMapping("/users")
    public List<User> getAllUsers () {
        return null;

    }

    /**
     * When invoking this endpoint provide the user identification and the complete user information,
     *  in result it will receive the updated user.
     * @param userId
     * @param user
     * @return all information of the updated user
     */
    @PutMapping("/user/{userId}")
    public User updateUser(@Valid Long userId, @Valid @RequestBody User user){
        return user;
        // userRepository.findById(userId);
        //TODO after find the user update info with user in body
    }

    /**
     * When invoking this endpoint provide the user identification
     *  and receive a list of contacts that are associated with that user.
     *
     * @param userId
     * @return a list of contacts
     */
    @GetMapping("/users/{userId}/contacts")
    public List<Contact> getAllUserContacts(@PathVariable Long userId){
        //return contactRepository.findByUserId(userId);
        return null;
    }


    /**
     * When invoking this endpoint provide the user identification and the new contact information,
     *  in return it will receive the newly created contact.
     *
     * @param userId
     * @param contact
     * @return the new contact updated
     */
    @PostMapping("/users/{userId}/contacts")
    public Contact addUserContact(@PathVariable Long userId,@Valid @RequestBody Contact contact){
        //TODO add the userid to the save of the contact
        return null;

    }

    /**
     * When invoking this endpoint provide the user identification,
     *  contact identification and the complete updated contact,
     *  in return it will receive the updated contact.
     *
     * @param userId
     * @param contactId
     * @param contact
     * @return the contact updated
     */
    @PutMapping("users/{userId}/contacts/{contactId}")
    public Contact updateContactOfUser(@PathVariable Long userId, @PathVariable Long contactId, @Valid @RequestBody Contact contact){
        //TODO search for this user contact and updated
        return contact;
    }



}
