package com.md.onboarding.service;

import com.md.onboarding.model.dto.ValidateMailRequest;

public interface MailConfirmationTokenService {

    boolean sendMailConfirmationToken(String mailClient) throws Exception;

    boolean validateMailConfirmationToken(ValidateMailRequest validateMailRequest) throws Exception;

    void cancelMailConfirmationTokenValidation(String mailClient) throws Exception;

}
