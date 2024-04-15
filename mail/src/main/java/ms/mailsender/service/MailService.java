package ms.mailsender.service;

public interface MailService {
    void sendSimpleMail(String[] toUsers, String subject, String message);
}
