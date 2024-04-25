package com.md.onboarding.repository;

import com.md.onboarding.model.entity.MailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailConfirmationTokenRepository extends JpaRepository<MailConfirmationToken, String> {
}
