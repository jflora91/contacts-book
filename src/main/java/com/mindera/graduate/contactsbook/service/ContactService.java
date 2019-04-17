package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.mapper.MapperConvert;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.ContactNumber;
import com.mindera.graduate.contactsbook.model.User;
import com.mindera.graduate.contactsbook.repository.ContactNumberRepository;
import com.mindera.graduate.contactsbook.repository.ContactRepository;
import com.mindera.graduate.contactsbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    private MapperConvert mapperConvert = new MapperConvert();


    /**
     * split the method addContact in 2(addOwnContact,addContact) because when
     * we add 1 user, we can add is own contact and when we convert the dto to entity we convert the contact too
     * so, we will receive one Contact and not a ContactDTO
     *
     * @param contact
     * @return
     */
    public Contact addOwnContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public ContactDTO addContact(Long userId, ContactDTO contactDTO) {

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID:" + userId + " doesn't exist");
        }

        if (contactDTO.getPhoneNumbers().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact must have one contact number");
        }

        for (String phoneNumber : contactDTO.getPhoneNumbers()) {
            if (!isValid(phoneNumber)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '" + phoneNumber + "' is not valid");
            }
        }

        Contact contact = mapperConvert.convertToContact(contactDTO);


        contact.setUser(user.get());
        contact = contactRepository.save(contact); // save the contact of this user

        List<ContactNumber> contactNumbersToSave = new ArrayList<>();
        for (String phoneNumber : contactDTO.getPhoneNumbers()) {
            ContactNumber contactNumber = new ContactNumber(phoneNumber, contact);
            contactNumbersToSave.add(contactNumber);
        }
        contactNumbersToSave = contactNumberRepository.saveAll(contactNumbersToSave); // save the phone numbers for this contact

        contactDTO = mapperConvert.convertToContactDTO(contact);

        contactDTO.setPhoneNumbers(contactNumbersToSave.stream()
                .map(x -> x.getPhoneNumber())
                .collect(Collectors.toList()));

        return contactDTO;

    }

    /**
     * get the user
     * get contacts of that user
     * save the user and the list of contacts in a new instance of UserContactsDTO
     * @param userId
     * @return
     */
    @Override
    public List<ContactDTO> findUserContacts(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID:" + userId + " doesn't exist");
        }

        List<Contact> contacts = contactRepository.findByUserId(userId);

        return contacts.stream()
                .map(contact -> toContactDTO(contact))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        return contacts.stream()
                .map(contact -> toContactDTO(contact))
                .collect(Collectors.toList());
    }

    /**
     * convert contact to contactDTO
     * get contactNumbers from this contact
     * get the phone numbers
     * return it in contactsDTO
     *
     * @param contact
     * @return
     */
    public ContactDTO toContactDTO(Contact contact) {
        ContactDTO contactDTO = mapperConvert.convertToContactDTO(contact);

        List<ContactNumber> contactNumbers = contactNumberRepository.findByContactId(contact.getId());
        contactDTO.setPhoneNumbers(contactNumbers.stream()
                .map(contactNumber -> contactNumber.getPhoneNumber())
                .collect(Collectors.toList()));
        return contactDTO;
    }

    @Override
    public List<ContactDTO> findByPhoneNumber(String phoneNumber) {

        if (!isValid(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '" + phoneNumber + "' is not valid");
        }

        List<ContactNumber> contactsNumbers = contactNumberRepository.findByPhoneNumber(phoneNumber);

        /**
         * get all contacts with that number
         * call getContactsDTO to convert the contacts and populate with the phone numbers
         */
        List<ContactDTO> contactsDTO = contactsNumbers.stream()
                .map(contactNumber -> toContactDTO(contactNumber.getContact()))
                .collect(Collectors.toList());

        return contactsDTO;
    }


    /**
     * Check if phone number is valid:
     * - Considering the public telecommunication numbering plan E.164 recommendation, the general format must contain
     * only digits split like:
     * country code(max 3 digits)
     * subscriber number (max 12 digits
     *
     * @param phoneNumber
     * @return
     */

    private boolean isValid(String phoneNumber) {

        /**
         * - Alternative formats (with area codes and country specific numbers) are available.
         * - plus symbol (+) auto-generated depending on location
         * - can have ()- symbols
         * - min size 3 digits
         * - max size 35 digits
         */
        return Pattern.matches("^(\\(?\\+?[0-9]{2,5}\\)?)? ?([0-9\\-\\(\\) ]){3,30}$", phoneNumber);
    }
}
