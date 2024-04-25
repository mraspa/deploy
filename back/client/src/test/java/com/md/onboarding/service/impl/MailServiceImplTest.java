package com.md.onboarding.service.impl;

import com.md.onboarding.JUtils.DataMailConfirmationToken;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.MailConfirmationToken;
import com.md.onboarding.msClient.MailSenderClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @Mock
    private MailSenderClient mailSenderClient;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    void sendMail_IsOk() {
        ValidateMailRequest validateMailRequest = DataMailConfirmationToken.givenValidateMailRequest();
        MailConfirmationToken mailConfirmationToken = DataMailConfirmationToken.givenMailConfirmationToken();

        mailService.sendMail(mailConfirmationToken);

        String expectedSubject = "Código de confirmación de mail";
        String expectedBody = "¡Hola!\nTu código es: " + validateMailRequest.token() + ".\nEs válido por 10 minutos.\nNo lo compartas con nadie.";

        verify(mailSenderClient, times(1)).mailSender(argThat(mailResponse ->
                mailResponse.toUsers().get(0).equals(validateMailRequest.mailClient()) &&
                        mailResponse.subject().equals(expectedSubject) &&
                        mailResponse.message().equals(expectedBody)
        ));
    }
}
