package ms.mailsender.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ms.mailsender.model.dto.MailSimpleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface MailApi {

    @Operation(summary = "Send simple message by email", description = "Send a text-only email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The email was sent successfully.")})
    @PostMapping("/sendSimpleMessage")
    public ResponseEntity<Map<String, String>> receiveRequestEmail(@RequestBody MailSimpleRequest mailRequest);
}
