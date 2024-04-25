package ms.mailsender.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Value("${mail.senderUsername}")
    private String mailSenderUsername;

    @Value("${mail.senderPassword}")
    private String mailSenderPassword;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender  = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(mailSenderUsername);
        mailSender.setPassword(mailSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");   // DEFINIMOS EL PROTOCOLO QUE UTIIZA EL PROVEEDOR PARA EL ENVIO DE COREOS ELECTRONICOS
        props.put("mail.smtp.auth", "true");   // HABILITAMOS LA AUTENTICACION MEDIANTE EL PROTOCOLO CON EL USERNAME Y PASSWORD DEFINIDOS
        props.put("mail.smtp.starttls.enable", "true"); // HABILITAMOS EL CIFRADO DE LA COMUNICACION ENTRE LA APLICACION Y EL PROVEEDOR DE CORREOS ELECTRONICOS
        props.put("mail.debug", "true");    // HABILITAMOS LA IMPRESION DE DATOS EN CONSOLA DE LA COMUNICACION ENTRE LA APLICACION Y EL PROVEEDOR

        return mailSender;
    }

}
