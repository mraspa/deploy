package ms.mailsender.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    public void sendSimpleMailOk() {
        String[] toUsers = {"user1@example.com", "user2@example.com"};
        String subject = "Subject";
        String message = "Message";

        mailService.sendSimpleMail(toUsers, subject, message);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

}