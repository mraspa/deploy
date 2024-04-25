package com.md.onboarding.JUtils;

import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.MailConfirmationToken;

import java.time.LocalDateTime;

public class DataMailConfirmationToken {

    public static MailConfirmationToken givenMailConfirmationToken() {
        return MailConfirmationToken.builder()
                .mailClient("mail_onboarding@gmail.com")
                .token(123456)
                .attempts(1)
                .timeStamp(LocalDateTime.now())
                .validated(false)
                .build();
    }

    public static MailConfirmationToken givenMailConfirmationToken_tokenException() {
        return MailConfirmationToken.builder()
                .mailClient("mail_onboarding@gmail.com")
                .token(789000)
                .attempts(1)
                .timeStamp(LocalDateTime.now())
                .validated(false)
                .build();
    }

    public static MailConfirmationToken givenMailConfirmationToken_validatedException() {
        return MailConfirmationToken.builder()
                .mailClient("mail_onboarding@gmail.com")
                .token(123456)
                .attempts(1)
                .timeStamp(LocalDateTime.now())
                .validated(true)
                .build();
    }

    public static MailConfirmationToken givenMailConfirmationToken_attemptsException() {
        return MailConfirmationToken.builder()
                .mailClient("mail_onboarding@gmail.com")
                .token(123456)
                .attempts(3)
                .timeStamp(LocalDateTime.of(2000, 4, 8, 12, 40, 30))
                .validated(false)
                .build();
    }

    public static MailConfirmationToken givenMailConfirmationToken_timeException() {
        return MailConfirmationToken.builder()
                .mailClient("mail_onboarding@gmail.com")
                .token(123456)
                .attempts(1)
                .timeStamp(LocalDateTime.of(2000, 4, 8, 12, 40, 30))
                .validated(false)
                .build();
    }

    public static ValidateMailRequest givenValidateMailRequest() {
        return new ValidateMailRequest("mail_onboarding@gmail.com",123456);
    }

}
