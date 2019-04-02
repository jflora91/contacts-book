package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacts")
public class Contact extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="contactId", nullable = false, updatable = false)
    private Long id;

    @Column(name="firstName")
    @Size(max=255)
    private String firstName;

    @Column(name="lastName",nullable = false)
    @Size(max=255)
    private String lastName;

    // getters and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
