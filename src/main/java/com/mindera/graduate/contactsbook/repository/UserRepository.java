package com.mindera.graduate.contactsbook.repository;

import com.mindera.graduate.contactsbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
