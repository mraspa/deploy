package com.md.account.exception.handler;

import com.md.account.exception.AccountNotFoundException;
import com.md.account.exception.AccountNumberAlreadyExistsException;
import com.md.account.exception.AliasAlreadyExistsException;
import com.md.account.exception.CBUAlreadyExistsException;
import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.exception.ClientNotFoundException;
import com.md.account.exception.InvalidJwtException;
import com.md.account.message.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> accountNumberAlreadyExistsException(AccountNumberAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(AliasAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> aliasAlreadyExistsException(AliasAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(CBUAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> cbuAlreadyExistsException(CBUAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> clientAlreadyExistsException(ClientAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ResponseMessage> clientNotFoundException(ClientNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseMessage> accountNotFoundException(AccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }


    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ResponseMessage> invalidJwtException(InvalidJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
