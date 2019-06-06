package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    /**
     * ownContact represent the contact of this user, this one can have his own contact.
     * It's splitted from the user contact list
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownContactId", referencedColumnName = "id")
    private Contact ownContact;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

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
