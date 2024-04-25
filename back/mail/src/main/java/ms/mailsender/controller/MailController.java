package ms.mailsender.controller;

import lombok.AllArgsConstructor;
import ms.mailsender.documentation.MailApi;
import ms.mailsender.model.dto.MailSimpleRequest;
import ms.mailsender.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("mail-sender/v1")
public class MailController implements MailApi {

    private final MailService mailService;

    @PostMapping("/sendSimpleMessage")
    @Override
    public ResponseEntity<Map<String, String>> receiveRequestEmail(@RequestBody MailSimpleRequest mailRequest) {
        mailService.sendSimpleMail(mailRequest.toUsers(), mailRequest.subject(), mailRequest.message());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }
}
