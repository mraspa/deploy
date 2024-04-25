package com.md.account.repository;

import com.md.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query(value = "SELECT MAX(a.id) FROM accounts a",nativeQuery = true)
    Long findMaxId();

    Optional<Account> findByAccountNumber(String accountNumber);



}
