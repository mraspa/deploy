package com.md.onboarding.service;

import com.md.onboarding.model.entity.MailConfirmationToken;

public interface MailService {

    void sendMail(MailConfirmationToken mailConfirmationToken);

}
