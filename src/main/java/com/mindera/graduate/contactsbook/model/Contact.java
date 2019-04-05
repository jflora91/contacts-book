package com.mindera.graduate.contactsbook.model;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "contacts")
public class Contact extends TimestampedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, updatable = false)
    private Long id;

    @Column(name="firstName")
    @Size(max=255)
    private String firstName;

    @Column(name="lastName")
    @Size(max=255)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    public Contact(@Size(max = 255) String firstName, @Size(max = 255) String lastName, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }

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
