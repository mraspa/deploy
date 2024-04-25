package com.md.login.repository;

import com.md.login.model.entity.RecoveryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryCodeRepository extends JpaRepository<RecoveryCode,Long> {


    Optional<RecoveryCode> findByGeneratedCode(String codeGenerated);
    Optional<RecoveryCode> findByUserEmail(String email);
}
