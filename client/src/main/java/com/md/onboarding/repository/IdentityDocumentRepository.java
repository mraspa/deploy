package com.md.onboarding.repository;

import com.md.onboarding.model.entity.IdentityDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument,Long> {

    Optional<IdentityDocument> findByIdNumber(Long idNumber);

}
