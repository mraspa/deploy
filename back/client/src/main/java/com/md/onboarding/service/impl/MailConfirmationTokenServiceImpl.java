package com.md.onboarding.service.impl;

import com.md.onboarding.exception.DifferentTokensException;
import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.exception.MailAlreadyValidatedException;
import com.md.onboarding.exception.MaximumAttemptsSurpassedException;
import com.md.onboarding.exception.ValidationTimeExceededException;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.MailConfirmationToken;
import com.md.onboarding.repository.MailConfirmationTokenRepository;
import com.md.onboarding.service.MailConfirmationTokenService;
import com.md.onboarding.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailConfirmationTokenServiceImpl implements MailConfirmationTokenService {

    private final MailConfirmationTokenRepository mailConfirmationTokenRepository;
    private final MailService mailService;

    @Override
    public boolean sendMailConfirmationToken(String mailClient) throws Exception {
        Optional<MailConfirmationToken> optional = mailConfirmationTokenRepository.findById(mailClient);

        int token = generateRandomToken();

        LocalDateTime timeStamp = obtainTimeStamp();

        MailConfirmationToken mailConfirmationToken;

        if (optional.isPresent()) {
            if (Boolean.TRUE.equals(optional.get().getValidated()))
                throw new MailAlreadyValidatedException("The client's email address is already validated.");

            if (optional.get().getAttempts() >= 3) {
                long timeDifference = Math.abs(ChronoUnit.HOURS.between(optional.get().getTimeStamp(), timeStamp));

                if (timeDifference >= 24)
                    resetMailConfirmationToken(optional.get());

                throw new MaximumAttemptsSurpassedException("The maximum 3 attempts for mail validation have already been exceeded.");
            }

            mailConfirmationToken = optional.get();
            mailConfirmationToken.setToken(token);
            mailConfirmationToken.setAttempts(optional.get().getAttempts() + 1);
            mailConfirmationToken.setTimeStamp(timeStamp);
        } else {
            mailConfirmationToken = MailConfirmationToken.builder()
                    .mailClient(mailClient)
                    .token(token)
                    .attempts(1)
                    .timeStamp(timeStamp)
                    .validated(false)
                    .build();
        }

        mailConfirmationTokenRepository.saveAndFlush(mailConfirmationToken);

        mailService.sendMail(mailConfirmationToken);

        return true;
    }

    @Override
    public boolean validateMailConfirmationToken(ValidateMailRequest validateMailRequest) throws Exception {
        Optional<MailConfirmationToken> optional = mailConfirmationTokenRepository.findById(validateMailRequest.mailClient());

        if (optional.isEmpty())
            throw new EntityNotFoundException("No client was found with that email.");

        if (Boolean.TRUE.equals(optional.get().getValidated()))
            throw new MailAlreadyValidatedException("The client's email address is already validated.");

        if (optional.get().getToken() != validateMailRequest.token())
            throw new DifferentTokensException("Validation tokens are different.");

        long timeDifference = Math.abs(ChronoUnit.MINUTES.between(optional.get().getTimeStamp(), obtainTimeStamp()));

        if (timeDifference >= 10)
            throw new ValidationTimeExceededException("The validation token has already expired.");

        optional.get().setValidated(true);

        mailConfirmationTokenRepository.saveAndFlush(optional.get());

        return true;
    }

    @Override
    public void cancelMailConfirmationTokenValidation(String mailClient) throws Exception {
        Optional<MailConfirmationToken> optional = mailConfirmationTokenRepository.findById(mailClient);

        if (optional.isEmpty())
            throw new EntityNotFoundException("No client was found with that email.");

        resetMailConfirmationToken(optional.get());
    }

    private static int generateRandomToken() {
        SecureRandom random = new SecureRandom();

        return 100000 + random.nextInt(900000);
    }

    private static LocalDateTime obtainTimeStamp() {
        ZoneId buenosAiresZone = ZoneId.of("America/Argentina/Buenos_Aires");

        return LocalDateTime.now(buenosAiresZone);
    }

    private void resetMailConfirmationToken(MailConfirmationToken mailConfirmationToken) {
        mailConfirmationToken.setToken(0);
        mailConfirmationToken.setTimeStamp(null);
        mailConfirmationToken.setAttempts(0);

        mailConfirmationTokenRepository.saveAndFlush(mailConfirmationToken);
    }

}
