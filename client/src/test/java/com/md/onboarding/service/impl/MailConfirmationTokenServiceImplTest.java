package com.md.onboarding.service.impl;

import com.md.onboarding.JUtils.DataMailConfirmationToken;
import com.md.onboarding.exception.DifferentTokensException;
import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.exception.MailAlreadyValidatedException;
import com.md.onboarding.exception.MaximumAttemptsSurpassedException;
import com.md.onboarding.exception.ValidationTimeExceededException;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.MailConfirmationToken;
import com.md.onboarding.repository.MailConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailConfirmationTokenServiceImplTest {

    @Mock
    private MailConfirmationTokenRepository mailConfirmationTokenRepository;

    @Mock
    private MailServiceImpl mailService;

    @InjectMocks
    private MailConfirmationTokenServiceImpl mailConfirmationTokenService;

    private MailConfirmationToken mailConfirmationToken;

    @BeforeEach
    void setUp() {
         mailConfirmationToken = DataMailConfirmationToken.givenMailConfirmationToken();
    }

    @Nested
    class sendMailConfirmationToken {
        @Test
        void sendMailConfirmationToken_EntityNotExists() throws Exception {
            Optional<MailConfirmationToken> optional = Optional.empty();

            when(mailConfirmationTokenRepository.findById(mailConfirmationToken.getMailClient())).thenReturn(optional);

            boolean result = mailConfirmationTokenService.sendMailConfirmationToken(mailConfirmationToken.getMailClient());

            assertTrue(result);
            verify(mailConfirmationTokenRepository).findById(any(String.class));
            verify(mailConfirmationTokenRepository, times(1)).findById(any(String.class));
            verify(mailConfirmationTokenRepository).saveAndFlush(any(MailConfirmationToken.class));
            verify(mailConfirmationTokenRepository, times(1)).saveAndFlush(any(MailConfirmationToken.class));
        }

        @Test
        void sendMailConfirmationToken_EntityExists() throws Exception {
            when(mailConfirmationTokenRepository.findById(mailConfirmationToken.getMailClient())).thenReturn(Optional.of(mailConfirmationToken));

            boolean result = mailConfirmationTokenService.sendMailConfirmationToken(mailConfirmationToken.getMailClient());

            assertTrue(result);
            verify(mailConfirmationTokenRepository).findById(mailConfirmationToken.getMailClient());
            verify(mailConfirmationTokenRepository, times(1)).findById(mailConfirmationToken.getMailClient());
            verify(mailConfirmationTokenRepository).saveAndFlush(mailConfirmationToken);
            verify(mailConfirmationTokenRepository, times(1)).saveAndFlush(mailConfirmationToken);
            verify(mailService).sendMail(mailConfirmationToken);
            verify(mailService, times(1)).sendMail(mailConfirmationToken);
        }

        @Test
        void sendMailConfirmationToken_ThrowsMailAlreadyValidatedException() {
            MailConfirmationToken mailConfirmationToken_validatedException = DataMailConfirmationToken.givenMailConfirmationToken_validatedException();
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken_validatedException);

            when(mailConfirmationTokenRepository.findById(mailConfirmationToken_validatedException.getMailClient())).thenReturn(optional);

            assertThrows(MailAlreadyValidatedException.class, () -> mailConfirmationTokenService.sendMailConfirmationToken(mailConfirmationToken_validatedException.getMailClient()));
        }

        @Test
        void sendMailConfirmationToken_ThrowsMaximumAttemptsSurpassedException() {
            MailConfirmationToken mailConfirmationToken_attemptsException = DataMailConfirmationToken.givenMailConfirmationToken_attemptsException();
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken_attemptsException);

            when(mailConfirmationTokenRepository.findById(mailConfirmationToken_attemptsException.getMailClient())).thenReturn(optional);

            assertThrows(MaximumAttemptsSurpassedException.class, () -> mailConfirmationTokenService.sendMailConfirmationToken(mailConfirmationToken_attemptsException.getMailClient()));
        }
    }

    @Nested
    class validateMailConfirmationToken {
        @Test
        void validateMailConfirmationToken_IsOk_ReturnsBoolean() throws Exception {
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken);
            ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();

            when(mailConfirmationTokenRepository.findById(validateMailRequest.mailClient())).thenReturn(optional);
            when(mailConfirmationTokenRepository.saveAndFlush(mailConfirmationToken)).thenReturn(mailConfirmationToken);

            boolean result = mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest);

            assertTrue(result);
            verify(mailConfirmationTokenRepository).findById(any(String.class));
            verify(mailConfirmationTokenRepository, times(1)).findById(any(String.class));
            verify(mailConfirmationTokenRepository).saveAndFlush(any(MailConfirmationToken.class));
            verify(mailConfirmationTokenRepository, times(1)).saveAndFlush(any(MailConfirmationToken.class));
        }

        @Test
        void validateMailConfirmationToken_ThrowsEntityNotFoundException() {
            Optional<MailConfirmationToken> optional = Optional.empty();
            ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();

            when(mailConfirmationTokenRepository.findById(validateMailRequest.mailClient())).thenReturn(optional);

            assertThrows(EntityNotFoundException.class, () -> mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest));
        }

        @Test
        void validateMailConfirmationToken_ThrowsMailAlreadyValidatedException() {
            MailConfirmationToken mailConfirmationToken_validatedException = DataMailConfirmationToken.givenMailConfirmationToken_validatedException();
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken_validatedException);
            ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();

            when(mailConfirmationTokenRepository.findById(validateMailRequest.mailClient())).thenReturn(optional);

            assertThrows(MailAlreadyValidatedException.class, () -> mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest));
        }

        @Test
        void validateMailConfirmationToken_ThrowsDifferentTokensException() {
            MailConfirmationToken mailConfirmationToken_tokenException = DataMailConfirmationToken.givenMailConfirmationToken_tokenException();
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken_tokenException);
            ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();

            when(mailConfirmationTokenRepository.findById(validateMailRequest.mailClient())).thenReturn(optional);

            assertThrows(DifferentTokensException.class, () -> mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest));
        }

        @Test
        void validateMailConfirmationToken_ThrowsValidationTimeExceededException() {
            MailConfirmationToken mailConfirmationToken_timeException = DataMailConfirmationToken.givenMailConfirmationToken_timeException();
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken_timeException);
            ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();

            when(mailConfirmationTokenRepository.findById(validateMailRequest.mailClient())).thenReturn(optional);

            assertThrows(ValidationTimeExceededException.class, () -> mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest));
        }
    }

    @Nested
    class cancelMailConfirmationTokenValidation {
        @Test
        void cancelMailConfirmationTokenValidation_isOk() {
            Optional<MailConfirmationToken> optional = Optional.of(mailConfirmationToken);

            when(mailConfirmationTokenRepository.findById(mailConfirmationToken.getMailClient())).thenReturn(optional);

            assertDoesNotThrow(() -> mailConfirmationTokenService.cancelMailConfirmationTokenValidation(mailConfirmationToken.getMailClient()));
        }

        @Test
        void cancelMailConfirmationTokenValidation_ThrowsEntityNotFoundException() {
            Optional<MailConfirmationToken> optional = Optional.empty();

            when(mailConfirmationTokenRepository.findById(mailConfirmationToken.getMailClient())).thenReturn(optional);

            assertThrows(EntityNotFoundException.class, () -> mailConfirmationTokenService.cancelMailConfirmationTokenValidation(mailConfirmationToken.getMailClient()));
        }
    }

}