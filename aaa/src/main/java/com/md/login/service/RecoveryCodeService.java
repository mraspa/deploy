package com.md.login.service;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.model.entity.RecoveryCode;


public interface RecoveryCodeService {

    void saveRecoveryCode(RecoveryRequestDto recoveryRequestDto);

    RecoveryCode getRecoveryCodeByUserEmail(String email) throws InvalidCodeException;

    void validateCode(ValidateCodeDto validateCodeDto) throws ExpiredCodeException, InvalidCodeException;
    void resetPassword(ResetPaswordDto resetPaswordDto) throws InvalidPasswordException;
}
