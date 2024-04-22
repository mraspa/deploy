package com.md.onboarding.repository;

import com.md.onboarding.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByMail(String mail);
    Optional<Contact> findByPhoneNumber(String phoneNumber);

}
