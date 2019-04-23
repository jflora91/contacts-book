package com.mindera.graduate.contactsbook.controller;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.service.ContactService;
import com.mindera.graduate.contactsbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

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
    public List<UserDTO> getAllUsers () {
        return userService.getAllUsers();

    }

    /**
     * When invoking this endpoint receive a user registered on the service
     * @param userId
     * @return UserDTO
     */
    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    /**
     * When invoking this endpoint, provide the user identification and the new contact information,
     *  in return it will receive the newly created contact
     * @param userId
     * @param contactDTO
     * @return
     */
    @PostMapping("/users/{id}/contacts")
    public ContactDTO addUserContact(@PathVariable("id") Long userId, @Valid @RequestBody ContactDTO contactDTO) {
        return contactService.addContact(userId, contactDTO);
    }

    /**
     * When invoking this endpoint provide the user identification and the complete user information,
     *  in result it will receive the updated user.
     * @param userId
     * @param userDTO
     * @return all information of the updated user
     */
    @PutMapping("/user/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    /**
     * When invoking this endpoint provide the user identification
     *  and receive a list of contacts that are associated with that user.
     *
     * @param userId
     * @return a list of contacts
     */
    @GetMapping("/users/{userId}/contacts")
    public List<ContactDTO> getAllUserContacts(@PathVariable Long userId){
        return contactService.findUserContacts(userId);
    }




}
