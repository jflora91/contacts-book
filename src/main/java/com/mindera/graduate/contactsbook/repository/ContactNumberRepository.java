package com.mindera.graduate.contactsbook.repository;

import com.mindera.graduate.contactsbook.model.ContactNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactNumberRepository extends JpaRepository<ContactNumber, Long> {
}
