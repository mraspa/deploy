package com.md.login.service.imp;

import com.md.login.exception.InvalidCodeException;
import com.md.login.model.dto.EmailDto;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.entity.RecoveryCode;
import com.md.login.msClient.MailSenderClient;
import com.md.login.utils.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.md.login.utils.TestEntityFactory.getRecoveryRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImpTest {

    @InjectMocks
    private EmailServiceImp emailServiceImp;

    @Mock
    private RecoveryRequestDto recoveryRequestDto;

    @Mock
    private UserServiceImp  userServiceImp;

    @Mock
    private RecoveryCodeServiceImp recoveryCodeServiceImp;

    @Mock
    private MailSenderClient mailSenderClient;

    @Test
    void testGetMaskedEmailExists() {

        when(userServiceImp.getEmail(recoveryRequestDto)).thenReturn("test@test.com");

        MaskedMailDto maskedMailDto = emailServiceImp.getMaskedEmail(recoveryRequestDto);


        assertEquals("***t@test.com", maskedMailDto.getMaskedMail());

    }

    @Test
     void testGetMaskedEmailNoExists() {

        when(userServiceImp.getEmail(recoveryRequestDto)).thenReturn(null);

        MaskedMailDto maskedMailDto = emailServiceImp.getMaskedEmail(recoveryRequestDto);


        assertEquals("***d*f*f*b*a*d*a@gmail.com", maskedMailDto.getMaskedMail());

    }

    @Test
    void sendEmail_withExistingEmail() throws InvalidCodeException {

        String email = TestEntityFactory.EMAIL;
        when(userServiceImp.getEmail(any(RecoveryRequestDto.class))).thenReturn(email);
        when(recoveryCodeServiceImp.getRecoveryCodeByUserEmail(email)).thenReturn(new RecoveryCode());


        emailServiceImp.sendEmail(getRecoveryRequestDto());

        verify(mailSenderClient, times(1)).emailSender(any(EmailDto.class));

    }

    @Test
    void sendEmail_withNullEmail() throws InvalidCodeException {

        when(userServiceImp.getEmail(any(RecoveryRequestDto.class))).thenReturn(null);

        emailServiceImp.sendEmail(new RecoveryRequestDto());

        verify(mailSenderClient, never()).emailSender(any(EmailDto.class));
    }

    @Test
    void sendEmail_withInvalidCode() throws InvalidCodeException {

        String email = TestEntityFactory.EMAIL;
        when(userServiceImp.getEmail(any(RecoveryRequestDto.class))).thenReturn(email);
        when(recoveryCodeServiceImp.getRecoveryCodeByUserEmail(email)).thenThrow(InvalidCodeException.class);


        assertThrows(InvalidCodeException.class, ()->emailServiceImp.sendEmail(getRecoveryRequestDto()) );

    }
}
