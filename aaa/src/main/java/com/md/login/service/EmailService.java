package com.md.login.service;

import com.md.login.exception.InvalidCodeException;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;

public interface EmailService {

    void sendEmail(RecoveryRequestDto recoveryRequestDto) throws InvalidCodeException;

    MaskedMailDto getMaskedEmail(RecoveryRequestDto recoveryRequestDto);


}
