package com.md.onboarding.model.dto;

import java.util.Arrays;
import java.util.List;

public record MailResponse(List<String> toUsers, String subject, String message) {

    public static MailResponse fromMailConfirmationToken(String mailUser, String subject, String message) {
        return new MailResponse(
                Arrays.asList(mailUser),
                subject,
                message);
    }

}
