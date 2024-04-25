package com.md.login.service.imp;

import com.md.login.exception.InvalidCodeException;
import com.md.login.model.dto.EmailDto;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.msClient.MailSenderClient;
import com.md.login.service.EmailService;
import com.md.login.service.RecoveryCodeService;
import com.md.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;



@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {


    private final UserService userService;
    private final RecoveryCodeService recoveryCodeService;
    private final MailSenderClient mailSenderClient;

    @Override
    public MaskedMailDto getMaskedEmail(RecoveryRequestDto recoveryRequestDto){

        String email=userService.getEmail(recoveryRequestDto);
        if(email==null){
            email= generateFakeEmail(recoveryRequestDto);
        }

        return MaskedMailDto.builder().maskedMail(maskEmail(email)).build();
    }
    @Override
    public void sendEmail(RecoveryRequestDto recoveryRequestDto) throws InvalidCodeException {
       String email=userService.getEmail(recoveryRequestDto);

        if(email!=null){
            EmailDto emailDto=EmailDto.builder()
                    .toUsers(List.of(email))
                    .subject("Solicitud de recupero de contraseña de MobyWallet")
                    .message(recoveryCodeService.getRecoveryCodeByUserEmail(email).getGeneratedCode())
                    .build();
            mailSenderClient.emailSender(emailDto);
        }

    }

    private String maskEmail(String email) {

        int atIndex = email.indexOf('@');

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        int usernameLength = username.length();

        int charactersToMask = Math.min(usernameLength / 2, 3);

        StringBuilder maskedUsername = new StringBuilder();
        for (int i = 0; i < usernameLength; i++) {
            char currentChar;
            if (i < charactersToMask) {
                currentChar = '*';
            } else {
                currentChar = (i % 2 == 0) ? '*' : username.charAt(i);
            }
            maskedUsername.append(currentChar);
        }
        return maskedUsername.toString() + domain;
    }


    private String generateFakeEmail(RecoveryRequestDto recoveryRequestDto) {

        String inputData = recoveryRequestDto.getTramitNumber() + recoveryRequestDto.getDocumentNumber();

        String hashedData = hashString(inputData);

        String emailLocalPart = extractAlphaChars(hashedData, 6, 16);

        return emailLocalPart + "@gmail.com";
    }

    private String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash", e);
        }
    }

    private String extractAlphaChars(String input, int minLength, int maxLength) {

        StringBuilder alphaChars = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                alphaChars.append(c);
            }
        }
        int length = Math.min(Math.max(alphaChars.length(), minLength), maxLength);
        return alphaChars.substring(0, length);
    }
}
