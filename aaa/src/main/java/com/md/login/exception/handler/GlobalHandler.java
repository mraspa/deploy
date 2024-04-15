package com.md.login.exception.handler;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidCredentialsException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.exception.UserAlreadyExistsException;
import com.md.login.exception.UserBlockedException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.message.ResponseMessage;
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
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> userAlreadyExistsException(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ResponseMessage> userBlockedException(UserBlockedException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseMessage> invalidCredentialsException(InvalidCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<ResponseMessage> expiredCodeException(ExpiredCodeException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ResponseMessage> invalidCodeException(InvalidCodeException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ResponseMessage> invalidPasswordException(InvalidPasswordException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.builder().message(exception.getMessage()).build());
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
