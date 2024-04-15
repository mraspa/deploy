package com.md.login.service.imp;

import com.md.login.exception.InvalidCredentialsException;
import com.md.login.exception.UserBlockedException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.jwt.JwtService;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.dto.UserDto;
import com.md.login.model.entity.User;
import com.md.login.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;



import java.util.Optional;

import static com.md.login.utils.TestEntityFactory.getAuthResponse;
import static com.md.login.utils.TestEntityFactory.getLoginRequestDto;
import static com.md.login.utils.TestEntityFactory.getUser;
import static com.md.login.utils.TestEntityFactory.getUserBlocked;
import static com.md.login.utils.TestEntityFactory.getUserDto;
import static com.md.login.utils.TestEntityFactory.getUserLastAttempt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class AuthServiceImpTest {
    @InjectMocks
    private AuthServiceImp authServiceImp;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    void loginTest() throws UserBlockedException {
        User user = getUser();
        UserDto userDto = getUserDto();
        LoginRequestDto loginRequestDto = getLoginRequestDto();
        AuthResponse authResponse = getAuthResponse();
        String jwt = authResponse.getJwt();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.ofNullable(user));
        when(jwtService.getToken(any(User.class))).thenReturn(jwt);

        AuthResponse result = authServiceImp.login(loginRequestDto);

        assertEquals(jwt, result.getJwt());
    }


    @Test
    void loginThrowsUserNotFoundException(){

        when(userRepository.findByEmail(getUser().getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->authServiceImp.login(getLoginRequestDto()));
    }

    @Test
    void loginThrowsUserBlockedException() {
        User user=getUserBlocked();
        UserDto userDto=getUserDto();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.ofNullable(user));

        assertThrows(UserBlockedException.class,()->authServiceImp.login(getLoginRequestDto()));
    }

    @Test
    void loginLastAttemptThrowsUserBlockedException() {
        User user=getUserLastAttempt();
        UserDto userDto=getUserDto();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.ofNullable(user));
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThrows(UserBlockedException.class,()->authServiceImp.login(getLoginRequestDto()));
    }

    @Test
    void loginThrowsInvalidCredentialsException() {
        User user=getUser();
        UserDto userDto=getUserDto();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.ofNullable(user));
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThrows(InvalidCredentialsException.class,()->authServiceImp.login(getLoginRequestDto()));
    }

}
