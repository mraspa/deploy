package com.md.onboarding.service.impl;

import com.md.onboarding.model.dto.MailResponse;
import com.md.onboarding.model.entity.MailConfirmationToken;
import com.md.onboarding.msClient.MailSenderClient;
import com.md.onboarding.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailSenderClient mailSenderClient;

    @Override
    public void sendMail(MailConfirmationToken mailConfirmationToken) {
        MailResponse mailResponse = MailResponse.fromMailConfirmationToken(
                mailConfirmationToken.getMailClient(),
                "Código de confirmación de mail",
                "¡Hola!\nTu código es: " + mailConfirmationToken.getToken() + ".\nEs válido por 10 minutos.\nNo lo compartas con nadie."
        );

        mailSenderClient.mailSender(mailResponse);
    }

}
