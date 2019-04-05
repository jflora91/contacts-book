package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacts_number")
public class ContactNumber extends TimestampedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, updatable = false)
    private Long id;

    @Column(name="phoneNumber")
    @Size(max=255)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    public ContactNumber(@Size(max = 255) String phoneNumber, Contact contact) {
        this.phoneNumber = phoneNumber;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
