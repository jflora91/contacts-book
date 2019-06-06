package com.mindera.graduate.contactsbook.mapper;


import com.mindera.graduate.contactsbook.dto.UserDTO;
import com.mindera.graduate.contactsbook.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;


public class MapperConvertTest {

    private static final Logger logger = LoggerFactory.getLogger(MapperConvertTest.class);

    MapperConvert mapperConvert = new MapperConvert();


    //todo @Test
    public void convertToUserDTO() {
        logger.error("not implemented yet!");
        fail();
    }

    @Test
    public void convertToUser() {

        UserDTO userDTO = new UserDTO(10L, "james", "john");
        logger.debug("Created userDTO: {}", userDTO);
        User user = mapperConvert.convertToUser(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertNull(userDTO.getPhoneNumbers());

    }

    //todo @Test
    public void convertToContactDTO() {
        logger.error("not implemented yet!");
        fail();

    }

    //todo @Test
    public void convertToContact() {
        fail();
    }
}