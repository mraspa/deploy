package com.md.login.service.imp;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.model.entity.RecoveryCode;
import com.md.login.model.entity.User;
import com.md.login.repository.RecoveryCodeRepository;
import com.md.login.repository.UserRepository;
import com.md.login.service.RecoveryCodeService;
import com.md.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecoveryCodeServiceImp implements RecoveryCodeService {
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final int CODE_LENGTH = 4;



    @Override
    public void saveRecoveryCode(RecoveryRequestDto recoveryRequestDto) {

        User user = getUser(recoveryRequestDto.getDocumentNumber(), recoveryRequestDto.getTramitNumber());
        if (user != null) {
            Optional<RecoveryCode> recoveryCode = recoveryCodeRepository.findByUserEmail(user.getEmail());
            if (recoveryCode.isPresent()) {
                recoveryCode.get().setValidated(false);
                recoveryCode.get().setGeneratedCode(generateRandomCode());
                recoveryCode.get().setExpires(LocalDateTime.now().plusMinutes(5));
                recoveryCodeRepository.save(recoveryCode.get());
            } else {
                this.recoveryCodeRepository.save(createRecoveryCode(user));
            }
        }

    }

    private User getUser(String documentNumber, String tramitNumber) {
        return userRepository.findByTramitNumberAndDocumentNumber(tramitNumber, documentNumber).orElse(null);
    }


    @Override
    public RecoveryCode getRecoveryCodeByUserEmail(String email) throws InvalidCodeException {
        return this.recoveryCodeRepository.findByUserEmail(email).orElseThrow(() -> new InvalidCodeException("Could not find recovery code"));
    }

    @Override
    public void validateCode(ValidateCodeDto validateCodeDto) throws ExpiredCodeException, InvalidCodeException {

        User user = getUser(validateCodeDto.getDocumentNumber(), validateCodeDto.getTramitNumber());

        if (user != null) {
            RecoveryCode recoveryCode = getRecoveryCodeByUserEmail(user.getEmail());
            if (!recoveryCode.getValidated()) {
                if (recoveryCode.getExpires().isBefore(LocalDateTime.now())) {
                    throw new ExpiredCodeException("This code is already expired");
                }
                if (!validateCodeDto.getCode().equals(recoveryCode.getGeneratedCode())) {
                    throw new InvalidCodeException("This code is not valid");
                }
                recoveryCode.setValidated(true);
                recoveryCode.setExpires((getExpirationTime()));
                recoveryCodeRepository.save(recoveryCode);
            } else {
                throw new InvalidCodeException("This code is already validated");
            }
        } else {
            throw new InvalidCodeException("This code is not valid");
        }
    }


    private RecoveryCode createRecoveryCode(User user) {
        return RecoveryCode.builder()
                .user(user)
                .validated(false)
                .generatedCode(generateRandomCode())
                .expires(getExpirationTime())
                .build();
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code;
        Random random;
        do {
            code = new StringBuilder();
            random = new Random();
            log.info("code generator");
            for (int i = 0; i < CODE_LENGTH; i++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }

        } while (verifyCode(code.toString()));
        log.info("code created");
        return code.toString();
    }

    private boolean verifyCode(String codeGenerated) {
        Optional<RecoveryCode> code = recoveryCodeRepository.findByGeneratedCode(codeGenerated);
        return code.isPresent();
    }

    @Override
    public void resetPassword(ResetPaswordDto resetPaswordDto) throws InvalidPasswordException {

        Optional<RecoveryCode> code = recoveryCodeRepository.findByGeneratedCode(resetPaswordDto.getCode());
        if (code.isEmpty() || code.get().getExpires().isBefore(LocalDateTime.now()) || !(code.get().getValidated())) {
            throw new InvalidPasswordException("This code is not valid");
        }
        String userEmail = code.get().getUser().getEmail();
        userService.resetPassword(resetPaswordDto.getPassword(), userEmail);
        code.get().setExpires(LocalDateTime.now());
        recoveryCodeRepository.save(code.get());
    }
    private LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusMinutes(5);
    }

}
