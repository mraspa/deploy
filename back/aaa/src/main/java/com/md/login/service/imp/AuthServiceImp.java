package com.md.login.service.imp;

import com.md.login.exception.InvalidCredentialsException;
import com.md.login.exception.UserBlockedException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.jwt.JwtService;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.entity.User;
import com.md.login.repository.UserRepository;
import com.md.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Integer MAX_FAILED_ATTEMPTS = 3;

    public AuthResponse login(LoginRequestDto request) throws UserBlockedException {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        try {
            if (user.getIsBloqued()) {
                throw new UserBlockedException("The account is blocked");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            resetLoginAttempts(user);
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .jwt(token)
                    .build();
        } catch (AuthenticationException e) {
            loginAttemptsCount(user);
            if (user.getAttempts().equals(MAX_FAILED_ATTEMPTS)) {
                blockAccount(user);
                throw new UserBlockedException("The account has been blocked");
            }
            throw new InvalidCredentialsException("invalid credentials");
        }
    }

    private void resetLoginAttempts(User user) {
        user.setAttempts(0);
        userRepository.save(user);
    }

    private void loginAttemptsCount(User user) {
        user.setAttempts(user.getAttempts() + 1);
        userRepository.save(user);
    }

    private void blockAccount(User user) {
        user.setIsBloqued(true);
        userRepository.save(user);
    }
}
