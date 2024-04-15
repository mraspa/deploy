package com.md.login.service;

import com.md.login.exception.InvalidPasswordException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.UserDto;

public interface UserService {

    void save(UserDto userDto);

    UserDto getUser(String email) throws UserNotFoundException;

    String getEmail(RecoveryRequestDto recoveryRequestDto);
    void  resetPassword(String password, String userEmail) throws InvalidPasswordException;
}
