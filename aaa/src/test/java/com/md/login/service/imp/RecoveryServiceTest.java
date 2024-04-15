package com.md.login.service.imp;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.model.entity.RecoveryCode;
import com.md.login.repository.RecoveryCodeRepository;
import com.md.login.repository.UserRepository;
import com.md.login.service.UserService;
import com.md.login.utils.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecoveryServiceTest {
    @Mock
    private RecoveryCodeRepository recoveryCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecoveryCodeServiceImp recoveryCodeServiceImp;

    @Test
    void saveRecoveryCodeTest() {
        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        RecoveryRequestDto recoveryRequestDto = TestEntityFactory.getRecoveryRequestDto();
        when(userRepository.findByTramitNumberAndDocumentNumber(TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER)).thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        recoveryCodeServiceImp.saveRecoveryCode(recoveryRequestDto);
        verify(recoveryCodeRepository).save(recoveryCode);
    }

    @Test
    void saveRecoveryCode_whenNotExistPreviusRecoveryCode_shouldCreateNewRecoveryCode() {
        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        RecoveryRequestDto recoveryRequestDto = TestEntityFactory.getRecoveryRequestDto();
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(null));
        recoveryCodeServiceImp.saveRecoveryCode(recoveryRequestDto);
        verify(recoveryCodeRepository).save(any(RecoveryCode.class));

    }

    @Test
    void getRecoveryCodeByUserEmailThrowsInvalidCodeException() throws InvalidCodeException {
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.empty());
        assertThrows(InvalidCodeException.class, () -> recoveryCodeServiceImp.getRecoveryCodeByUserEmail(TestEntityFactory.EMAIL));
    }

    @Test
    void getRecoveryCodeByUserEmailTest() throws InvalidCodeException {
        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        RecoveryCode searchedRecoveryCode = recoveryCodeServiceImp.getRecoveryCodeByUserEmail(TestEntityFactory.EMAIL);
        assertNotNull(searchedRecoveryCode);
        assertEquals(searchedRecoveryCode, recoveryCode);

    }

    @Test
    void validateRecoveryCodeTest() throws InvalidCodeException, ExpiredCodeException {
        ValidateCodeDto validateCodeDto = TestEntityFactory.getValidateCodeDto();
        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        recoveryCodeServiceImp.validateCode(validateCodeDto);
        assertTrue(recoveryCode.getValidated());
    }

    @Test
    void validateRecoveryCode_ThrowsInvalidCodeException() throws InvalidCodeException, ExpiredCodeException {
        ValidateCodeDto validateCodeDto = TestEntityFactory.getValidateCodeDto();

        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        recoveryCode.setGeneratedCode("fets");
        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        assertThrows(InvalidCodeException.class, () -> recoveryCodeServiceImp.validateCode(validateCodeDto));
    }

    @Test
    void validateRecoveryCode_ThrowsExpiredCodeException() throws InvalidCodeException, ExpiredCodeException {
        ValidateCodeDto validateCodeDto = TestEntityFactory.getValidateCodeDto();

        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        recoveryCode.setExpires(LocalDateTime.now().minusMinutes(5));

        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        assertThrows(ExpiredCodeException.class, () -> recoveryCodeServiceImp.validateCode(validateCodeDto));
    }

    @Test
    void validateRecoveryCode_InvalidCode() throws InvalidCodeException, ExpiredCodeException {
        ValidateCodeDto validateCodeDto = TestEntityFactory.getValidateCodeDto();

        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        recoveryCode.setValidated(true);

        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(TestEntityFactory.getUser()));
        when(recoveryCodeRepository.findByUserEmail(TestEntityFactory.EMAIL)).thenReturn(Optional.ofNullable(recoveryCode));
        assertThrows(InvalidCodeException.class, () -> recoveryCodeServiceImp.validateCode(validateCodeDto));
    }

    @Test
    void validateRecoveryCode_UserNotFount_InvalidCode() throws InvalidCodeException, ExpiredCodeException {
        ValidateCodeDto validateCodeDto = TestEntityFactory.getValidateCodeDto();

        when(userRepository.findByTramitNumberAndDocumentNumber(
                TestEntityFactory.TRAMIT_NUMBER, TestEntityFactory.DOCUMENT_NUMBER))
                .thenReturn(Optional.ofNullable(null));
        assertThrows(InvalidCodeException.class, () -> recoveryCodeServiceImp.validateCode(validateCodeDto));
    }

    @Test
    void resetPassword() throws Exception {
        ResetPaswordDto resetPaswordDto = TestEntityFactory.getResetPasswordDto();


        RecoveryCode recoveryCode = TestEntityFactory.getRecoveryCode();
        recoveryCode.setValidated(true);

        when(recoveryCodeRepository.findByGeneratedCode(resetPaswordDto.getCode()))
                .thenReturn(Optional.ofNullable(recoveryCode));
        recoveryCodeServiceImp.resetPassword(resetPaswordDto);
        verify(recoveryCodeRepository).save(any(RecoveryCode.class));
    }
}
