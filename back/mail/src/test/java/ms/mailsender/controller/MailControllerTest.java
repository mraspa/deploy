package ms.mailsender.controller;

import ms.mailsender.model.dto.MailSimpleRequest;
import ms.mailsender.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @Mock
    private MailService mailService;

    @InjectMocks
    private MailController mailController;

    @Test
    public void testReceiveRequestEmail() {
        MailSimpleRequest mailRequest = new MailSimpleRequest(new String[]{"user1@example.com"},"Subject","Message");
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("estado", "Enviado");

        doNothing().when(mailService).sendSimpleMail(any(String[].class), anyString(), anyString());

        ResponseEntity<Map<String, String>> response = mailController.receiveRequestEmail(mailRequest);

        verify(mailService, times(1)).sendSimpleMail(any(String[].class), anyString(), anyString());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null && response.getBody().equals(expectedResponse);
    }
}