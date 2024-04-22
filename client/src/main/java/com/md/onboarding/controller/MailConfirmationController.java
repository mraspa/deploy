package com.md.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.documentation.MailConfirmationApi;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.service.ClientDetailService;
import com.md.onboarding.service.MailConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("onboarding/v1/mails/")
public class MailConfirmationController implements MailConfirmationApi {

    private final MailConfirmationTokenService mailConfirmationTokenService;

    @PostMapping("/sendMailConfirmationToken/{mailClient}")
    @Override
    public ResponseEntity<Boolean> sendMailConfirmationToken(@PathVariable("mailClient") String mailClient) throws Exception {
        return new ResponseEntity<>(mailConfirmationTokenService.sendMailConfirmationToken(mailClient), HttpStatus.OK);
    }

    @PostMapping("/validateMailConfirmationToken")
    @Override
    public ResponseEntity<Boolean> validateMailConfirmationToken(@RequestBody ValidateMailRequest validateMailRequest) throws Exception {
        return new ResponseEntity<>(mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest), HttpStatus.OK);
    }

    @PostMapping("/cancelMailConfirmationTokenValidation/{mailClient}")
    @Override
    public ResponseEntity<Void> cancelMailConfirmationTokenValidation(@PathVariable("mailClient") String mailClient) throws Exception {
        mailConfirmationTokenService.cancelMailConfirmationTokenValidation(mailClient);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
