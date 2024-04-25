package com.md.login.controller;

import com.md.login.documentation.RecoveryPasswordApi;
import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.service.EmailService;
import com.md.login.service.RecoveryCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/password")
@Validated
public class RecoveryPasswordController implements RecoveryPasswordApi {
    private final RecoveryCodeService recoveryCodeService;
    private final EmailService emailService;

    @PostMapping("/recovery")
    @Override
    public ResponseEntity<MaskedMailDto> passwordRecovery(@Valid @RequestBody RecoveryRequestDto recoveryRequestDto) throws InvalidCodeException {

        recoveryCodeService.saveRecoveryCode(recoveryRequestDto);
        emailService.sendEmail(recoveryRequestDto);
        return new ResponseEntity<>(emailService.getMaskedEmail(recoveryRequestDto), HttpStatus.OK);
    }

    @PostMapping("/validate-code")
    @Override
    public ResponseEntity<HttpStatus> validateCode(@Valid @RequestBody ValidateCodeDto validateCodeDto) throws ExpiredCodeException, InvalidCodeException {
        recoveryCodeService.validateCode(validateCodeDto);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/reset")
    @Override
    public ResponseEntity<HttpStatus> passwordReset(@Valid @RequestBody ResetPaswordDto resetPaswordDto) throws  InvalidPasswordException {
        recoveryCodeService.resetPassword(resetPaswordDto);
        return ResponseEntity.ok().build();
    }
}
