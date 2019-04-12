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
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    private MapperConvert mapperConvert = new MapperConvert();

    /**
     * split the method addContact in 2(addOwnContact,addContact) because when
     *  we add 1 user, we can add is own contact and when we convert the dto to entity we convert the contact too
     *  so, we will receive one Contact and not a ContactDTO
     * @param contact
     * @return
     */
    public Contact addOwnContact(Contact contact){
        return contactRepository.save(contact);
    }

    @Override
    public ContactDTO addContact(Long userId, ContactDTO contactDTO) {

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID:"+userId+" doesn't exist");
        }

        if (contactDTO.getPhoneNumbers().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact must have one contact number");
        }

        for (String phoneNumber: contactDTO.getPhoneNumbers() ) {
            if (!isValid(phoneNumber)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact number '"+ phoneNumber +"' is not valid");
            }
        }

        Contact contact = mapperConvert.convertToContact(contactDTO);



        contact.setUser(user.get());
        contact = contactRepository.save(contact); // save the contact of this user

        List<ContactNumber> contactNumbersToSave = new ArrayList<>();
        for (String phoneNumber: contactDTO.getPhoneNumbers()) {
            ContactNumber contactNumber = new ContactNumber(phoneNumber, contact);
            contactNumbersToSave.add(contactNumber);
        }
        contactNumbersToSave = contactNumberRepository.saveAll(contactNumbersToSave); // save the phone numbers for this contact

        contactDTO = mapperConvert.convertToContactDTO(contact);

        contactDTO.setPhoneNumbers(contactNumbersToSave.stream()
                .map(x->x.getPhoneNumber())
                .collect(Collectors.toList()));

        return contactDTO;

    }

    private boolean isValid(String phoneNumber) {
        if (!phoneNumber.isBlank()){
            return true;
        }
        return false;
    }

    @Override
    public List<Contact> findUserContacts(Long userId) {

        return null;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        List<ContactDTO>contactsDTO = contacts.stream()
                .map(contact -> {
                        ContactDTO contactDTO = mapperConvert.convertToContactDTO(contact);

                        List<ContactNumber> contactNumbers = contactNumberRepository.findByContactId(contact.getId());

                        contactDTO.setPhoneNumbers(contactNumbers.stream()
                                .map(contactNumber -> contactNumber.getPhoneNumber())
                                .collect(Collectors.toList()));
                        return contactDTO;

                }).collect(Collectors.toList());

        return contactsDTO;
    }
}
