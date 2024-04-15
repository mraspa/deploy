package ms.mailsender.service.impl;

import lombok.AllArgsConstructor;
import ms.mailsender.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.senderUsername}")
    private String mailSenderUsername;

    @Override
    public void sendSimpleMail(String[] toUsers, String subject, String message){
        // DEFINIMOS EL OBJETO QUE NOS VA A AYUDAR A CONSTRUIR NUESTRO MENSAJE
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // PERSONALIZAMOS EL EMAIL
        mailMessage.setFrom(mailSenderUsername);
        mailMessage.setTo(toUsers);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        // ENVIAMOS EL CORREO ELECTRONICO
        mailSender.send(mailMessage);
    }

}
