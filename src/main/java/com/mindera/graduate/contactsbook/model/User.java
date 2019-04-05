package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name="users")
public class User extends TimestampedEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    /**
     * ownContact represent the contact of this user, this one can have his own contact.
     * It's splitted from the user contact list
     */
    @OneToOne
    @JoinColumn(name="ownContactId", referencedColumnName = "id")
    private Contact ownContact;

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
