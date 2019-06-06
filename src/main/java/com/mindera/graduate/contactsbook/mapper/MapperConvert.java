package com.mindera.graduate.contactsbook.mapper;

import com.mindera.graduate.contactsbook.dto.ContactDTO;
import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class MapperConvert {

    private static MapperConvert single_instance = null;
    ma.glasnost.orika.MapperFactory mapperFactory;
    MapperFacade mapperFacade;

    public MapperConvert() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public UserDTO convertToUserDTO(User user) {
        // implementation without orika
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName());
        return userDTO;
    }

    public User convertToUser(UserDTO userDTO) {
        return mapperFacade.map(userDTO, User.class);
    }

    public ContactDTO convertToContactDTO(Contact contact) {

        return mapperFacade.map(contact, ContactDTO.class);
    }

    public Contact convertToContact(ContactDTO contactDTO) {
        return mapperFacade.map(contactDTO, Contact.class);
    }


}
