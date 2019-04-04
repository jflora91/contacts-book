package com.mindera.graduate.contactsbook.repository;

import com.mindera.graduate.contactsbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
