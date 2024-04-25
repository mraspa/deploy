package com.md.login.exception.handler;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidCredentialsException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.exception.UserAlreadyExistsException;
import com.md.login.exception.UserBlockedException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.message.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalHandlerTest {

    GlobalHandler globalHandler;

    @BeforeEach
    void setUp() {
        globalHandler = new GlobalHandler();
    }

    @Test
    void userNotFoundException() {

        UserNotFoundException userNotFoundException = new UserNotFoundException("User not found");
        ResponseEntity<ResponseMessage> response = globalHandler.userNotFoundException(userNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void userAlreadyExistsException() {
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException("User already exists");
        ResponseEntity<ResponseMessage> response = globalHandler.userAlreadyExistsException(userAlreadyExistsException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void userBlockedException() {
        UserBlockedException userBlockedException = new UserBlockedException("User is blocked");
        ResponseEntity<ResponseMessage> response = globalHandler.userBlockedException(userBlockedException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User is blocked", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void invalidCredentialsException() {
        InvalidCredentialsException invalidCredentialsException = new InvalidCredentialsException("Invalid credentials");
        ResponseEntity<ResponseMessage> response = globalHandler.invalidCredentialsException(invalidCredentialsException);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void expiredCodeException() {
        ExpiredCodeException expiredCodeException = new ExpiredCodeException("Code is expired");
        ResponseEntity<ResponseMessage> response = globalHandler.expiredCodeException(expiredCodeException);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Code is expired", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void invalidCodeException() {
        InvalidCodeException invalidCodeException = new InvalidCodeException("Invalid code");
        ResponseEntity<ResponseMessage> response = globalHandler.invalidCodeException(invalidCodeException);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid code", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void invalidPasswordException() {
        InvalidPasswordException invalidPasswordException = new InvalidPasswordException("Invalid password");
        ResponseEntity<ResponseMessage> response = globalHandler.invalidPasswordException(invalidPasswordException);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid password", Objects.requireNonNull(response.getBody()).getMessage());
    }


    @Test
    void handleMethodArgumentNotValid() {

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, createBindingResult());
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        WebRequest request = mock(WebRequest.class);


        GlobalHandler globalHandler = new GlobalHandler();
        ResponseEntity<Object> responseEntity = globalHandler.handleMethodArgumentNotValid(ex, headers, status, request);


        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        Object body = responseEntity.getBody();
        assertNotNull(body);
        assertTrue(body instanceof Map);

        Map<String, Object> errors = (Map<String, Object>) body;
        assertEquals(2, errors.size());
        assertEquals("Error message 1", errors.get("fieldName1"));
        assertEquals("Error message 2", errors.get("fieldName2"));
    }

    private BindingResult createBindingResult() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("objectName", "fieldName1", "Error message 1");
        FieldError fieldError2 = new FieldError("objectName", "fieldName2", "Error message 2");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));
        return bindingResult;
    }
}
