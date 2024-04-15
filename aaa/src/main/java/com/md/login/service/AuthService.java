package com.md.login.service;

import com.md.login.exception.UserBlockedException;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;

public interface AuthService {
    AuthResponse login(LoginRequestDto request) throws UserBlockedException;
}
