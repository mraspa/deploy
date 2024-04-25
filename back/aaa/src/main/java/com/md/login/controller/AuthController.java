package com.md.login.controller;

import com.md.login.documentation.AuthApi;
import com.md.login.exception.UserBlockedException;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.dto.UserDto;
import com.md.login.service.AuthService;
import com.md.login.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController implements AuthApi {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Override
    public ResponseEntity<HttpStatus> save(@Valid @RequestBody UserDto userDto) {
        userService.save(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto request) throws UserBlockedException {
        return ResponseEntity.ok(authService.login(request));
    }


}
