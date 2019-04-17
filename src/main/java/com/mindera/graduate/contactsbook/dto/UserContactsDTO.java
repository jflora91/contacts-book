package com.mindera.graduate.contactsbook.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserContactsDTO {
    @NotNull
    private UserDTO userDTO;

    private List<ContactDTO> contactsDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<ContactDTO> getContactsDTO() {
        return contactsDTO;
    }

    public void setContactsDTO(List<ContactDTO> contactsDTO) {
        this.contactsDTO = contactsDTO;
    }
}
