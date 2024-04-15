package ms.mailsender.model.dto;

public record MailSimpleRequest (String[] toUsers, String subject, String message) { }
