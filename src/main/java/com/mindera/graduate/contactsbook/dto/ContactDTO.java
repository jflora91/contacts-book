package com.mindera.graduate.contactsbook.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactDTO {

    @Size(max = 255)
    private String firstName;
    @NotNull
    @Size(max = 255)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
