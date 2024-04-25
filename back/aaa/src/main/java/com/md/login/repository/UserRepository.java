package com.md.login.repository;

import com.md.login.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByTramitNumberAndDocumentNumber(String tramitNumber, String documentNumber);
}
