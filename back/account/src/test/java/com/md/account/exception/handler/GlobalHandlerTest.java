package com.md.account.exception.handler;

import com.md.account.exception.AccountNotFoundException;
import com.md.account.exception.AccountNumberAlreadyExistsException;
import com.md.account.exception.AliasAlreadyExistsException;
import com.md.account.exception.CBUAlreadyExistsException;
import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.exception.ClientNotFoundException;
import com.md.account.exception.InvalidJwtException;
import com.md.account.message.ResponseMessage;
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
    void accountNumberAlreadyExistsException() {
        AccountNumberAlreadyExistsException accountNumberAlreadyExistsException = new AccountNumberAlreadyExistsException("Account already exists");
        ResponseEntity<ResponseMessage> response = globalHandler.accountNumberAlreadyExistsException(accountNumberAlreadyExistsException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Account already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void aliasAlreadyExistsException() {
        AliasAlreadyExistsException aliasAlreadyExistsException = new AliasAlreadyExistsException("Alias already exists");
        ResponseEntity<ResponseMessage> response = globalHandler.aliasAlreadyExistsException(aliasAlreadyExistsException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Alias already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void cbuAlreadyExistsException() {
        CBUAlreadyExistsException cbuAlreadyExistsException = new CBUAlreadyExistsException("CBU already exists");
        ResponseEntity<ResponseMessage> response = globalHandler.cbuAlreadyExistsException(cbuAlreadyExistsException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("CBU already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void clientAlreadyExistsException() {
        ClientAlreadyExistsException clientAlreadyExistsException = new ClientAlreadyExistsException("Client already exists");
        ResponseEntity<ResponseMessage> response = globalHandler.clientAlreadyExistsException(clientAlreadyExistsException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Client already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void clientNotFoundException() {
        ClientNotFoundException clientNotFoundException = new ClientNotFoundException("Client not found");
        ResponseEntity<ResponseMessage> response = globalHandler.clientNotFoundException(clientNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Client not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void accountNotFoundException() {
        AccountNotFoundException accountNotFoundException = new AccountNotFoundException("Account not found");
        ResponseEntity<ResponseMessage> response = globalHandler.accountNotFoundException(accountNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void invalidJwtException() {
        InvalidJwtException invalidJwtException = new InvalidJwtException("Invalid token");
        ResponseEntity<ResponseMessage> response = globalHandler.invalidJwtException(invalidJwtException);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid token", Objects.requireNonNull(response.getBody()).getMessage());
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