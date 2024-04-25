package com.md.login.utils;

import com.md.login.model.Role;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.UserDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.model.entity.RecoveryCode;
import com.md.login.model.entity.User;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class TestEntityFactory {
    public static final Long ID_USER=1L;
    public static final String EMAIL="jorge@gmail.com";
    public static final String MASKED_EMAIL="***g*@gmail.com";
    public static final String PASSWORD="Jorge135";
    public static final String NEW_PASSWORD="Jorge666";
    public static final String CODE="IGF4";

    public static final LocalDateTime EXPIRATION_TIME=LocalDateTime.now().plusMinutes(5);

    public static final String PASSWORD_ENCODED="fsgtsertnnutruyeae";
    public static final String NEW_PASSWORD_ENCODED="RGTAELYKJTDSKFULDUKF";

    public static final String DOCUMENT_NUMBER="12345678";

    public static final String TRAMIT_NUMBER="12345678910";

    public static final String JWT="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiJ9.0zTcMxRvD9TS2gKoHjf5lE_CkITLCdC3N9Zu-QyR_8I";

    public static final Role ROLE=Role.USER;

    public static final Integer ATTEMPTS=0;

    public static final Integer MAX_ATTEMPTS=3;

    public static final Integer LAST_ATTEMPT=2;

    private TestEntityFactory(){
        throw new UnsupportedOperationException("this is an utility class and cannot be instanciated");
    };

    public static User getUser(){
        return User.builder()
                .id(ID_USER)
                .email(EMAIL)
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .password(PASSWORD_ENCODED)
                .role(ROLE)
                .isBloqued(FALSE)
                .attempts(ATTEMPTS)
                .build();
    }
    public static User getUserBlocked(){
        return User.builder()
                .id(ID_USER)
                .email(EMAIL)
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .password(PASSWORD_ENCODED)
                .role(ROLE)
                .isBloqued(TRUE)
                .attempts(MAX_ATTEMPTS)
                .build();
    }
    public static User getUserLastAttempt(){
        return User.builder()
                .id(ID_USER)
                .email(EMAIL)
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .password(PASSWORD_ENCODED)
                .role(ROLE)
                .isBloqued(FALSE)
                .attempts(LAST_ATTEMPT)
                .build();
    }
    public static UserDto getUserDto(){
        return UserDto.builder()
                .email(EMAIL)
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .password(PASSWORD)
                .build();
    }

    public static LoginRequestDto getLoginRequestDto(){
        return LoginRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static AuthResponse getAuthResponse(){
        return AuthResponse.builder()
                .jwt(JWT)
                .build();
    }
    public static User getUserUpdatedPassword(){
        return User.builder()
                .id(ID_USER)
                .email(EMAIL)
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .password(NEW_PASSWORD_ENCODED)
                .role(ROLE)
                .isBloqued(FALSE)
                .attempts(ATTEMPTS)
                .build();
    }

    public static RecoveryRequestDto getRecoveryRequestDto(){
        return RecoveryRequestDto.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .build();
    }


    public static MaskedMailDto getMaskedMailDto(){
        return MaskedMailDto.builder()
                .maskedMail(MASKED_EMAIL)
                .build();
    }

    public static ValidateCodeDto getValidateCodeDto(){
        return ValidateCodeDto.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .tramitNumber(TRAMIT_NUMBER)
                .code(CODE)
                .build();
    }

    public static RecoveryCode getRecoveryCode(){
        return RecoveryCode.builder()
                .id(1L)
                .user(getUser())
                .expires(EXPIRATION_TIME)
                .validated(false)
                .generatedCode(CODE)
                .build();
    }

    public static RecoveryCode getRecoveryCodeValidated(){
        return RecoveryCode.builder()
                .id(1L)
                .user(getUser())
                .expires(EXPIRATION_TIME)
                .validated(true)
                .generatedCode(CODE)
                .build();
    }


    public static ResetPaswordDto getResetPasswordDto(){
        return  ResetPaswordDto.builder()
                .code(CODE)
                .password(NEW_PASSWORD)
                .build();
    }

}
