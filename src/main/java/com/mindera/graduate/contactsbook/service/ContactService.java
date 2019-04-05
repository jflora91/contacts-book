package com.mindera.graduate.contactsbook.service;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.repository.ContactRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactService implements IContactService{

    @Autowired
    private ContactRepository contactRepository;

    /**
     * MapperFactory use to configure mappings
     * MapperFacade obtained from MapperFactory which performs the actual mapping work
     */
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();



    /**
     * receive a dto and convert to a entity representing the DB
     * @param contactDTO
     * @return
     */
    private Contact convertToEntity(ContactDTO contactDTO) {
        mapperFactory.classMap(ContactDTO.class, Contact.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Contact contact = mapper.map(contactDTO, Contact.class);
        return contact;
    }

    /**
     * receive a entity and convert to a DTObject
     * @param contact
     * @return
     */
    private ContactDTO convertToDTO(Contact contact) {
        mapperFactory.classMap(Contact.class, ContactDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        ContactDTO contactDTO = mapper.map(contact, ContactDTO.class);
        return contactDTO;
    }

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

    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        return convertToDTO(contactRepository.save(contact));
    }
}
