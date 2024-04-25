package com.md.login.service.imp;

import com.md.login.exception.InvalidPasswordException;
import com.md.login.exception.UserAlreadyExistsException;
import com.md.login.mapper.UserMapper;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.UserDto;
import com.md.login.model.entity.User;
import com.md.login.repository.UserRepository;
import com.md.login.utils.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.md.login.utils.TestEntityFactory.getUser;
import static com.md.login.utils.TestEntityFactory.getUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @InjectMocks
    private UserServiceImp userServiceImp;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void saveTest() {
        UserDto userDto = getUserDto();
        User user = getUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        userServiceImp.save(userDto);

        verify(userRepository, times(1)).save(user);

    }

    @Test
    void saveAlreadyExistsExceptionTest() {
        UserDto userDto = getUserDto();
        User user = getUser();

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.ofNullable(user));

        assertThrows(UserAlreadyExistsException.class, () -> userServiceImp.save(userDto));
    }
    @Test
    void saveAlreadyExistsExceptionFindByTramitNumberAndDocNumberTest() {
        UserDto userDto = getUserDto();
        User user = getUser();

        when(userRepository.findByTramitNumberAndDocumentNumber(userDto.getTramitNumber(),userDto.getDocumentNumber())).thenReturn(Optional.ofNullable(user));

        assertThrows(UserAlreadyExistsException.class, () -> userServiceImp.save(userDto));
    }

    @Test
    void getUserTest() {

        User user = getUser();
        UserDto userDto = getUserDto();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertEquals(userDto, userServiceImp.getUser(userDto.getEmail()));

    }

    @Test
    void getEmailTest() {

        User user = getUser();
        when(userRepository.findByTramitNumberAndDocumentNumber(user.getTramitNumber(), user.getDocumentNumber())).thenReturn(Optional.of(user));

        // when
        String result = userServiceImp.getEmail(new RecoveryRequestDto(user.getDocumentNumber(), user.getTramitNumber()));

        // then
        assertEquals(user.getEmail(), result);
    }

    @Test
    void testGetEmail_whenUserDoesNotExist_thenReturnNull() {
        User user = getUser();
        when(userRepository.findByTramitNumberAndDocumentNumber(user.getTramitNumber(), user.getDocumentNumber())).thenReturn(Optional.empty());

        String result = userServiceImp.getEmail(new RecoveryRequestDto(user.getDocumentNumber(), user.getTramitNumber()));

        assertNull(result);
    }
    @Test
    void resetPassword() throws InvalidPasswordException {

        User user = getUser();
        String newPassword = TestEntityFactory.NEW_PASSWORD_ENCODED;
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);

        userServiceImp.resetPassword(newPassword, user.getEmail());

        verify(userRepository).save(user);
    }
    @Test
    void resetPasswordSameAsPreviousOneTest() {
        User user = getUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

        assertThrows(InvalidPasswordException.class, () -> userServiceImp.resetPassword(user.getPassword(), user.getEmail()));
    }


}