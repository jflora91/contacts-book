package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity //an ordinary Java class that is annotated as having the ability to represent objects in the database
@Table(name="users")
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userId", updatable = false, nullable = false)
    private Long id;

    @Column(name="firstName", nullable = false)
    @Size(max = 255)
    private String firstName;

    @Column(name="lastName", nullable = false)
    @Size(max = 255)
    private String lastName;

    @Column(name="ownContact")
    private Contact ownContact;

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

    public Contact getOwnContact() {
        return ownContact;
    }

    public void setOwnContact(Contact ownContact) {
        this.ownContact = ownContact;
    }
}
