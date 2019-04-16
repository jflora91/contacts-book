package com.mindera.graduate.contactsbook.repository;

import com.mindera.graduate.contactsbook.model.Contact;
import com.mindera.graduate.contactsbook.model.ContactNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactNumberRepository extends JpaRepository<ContactNumber, Long> {
    List<ContactNumber> findByContact(Contact contact);

    List<ContactNumber> findByPhoneNumber(String phoneNumber);

    List<ContactNumber> findByContactId(Long id);
    
}
