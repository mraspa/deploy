package com.md.login.service.imp;

import com.md.login.exception.InvalidPasswordException;
import com.md.login.exception.UserAlreadyExistsException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.mapper.UserMapper;
import com.md.login.model.Role;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.UserDto;
import com.md.login.model.entity.User;
import com.md.login.repository.UserRepository;
import com.md.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Integer ATTEMPTS=0;
    private final boolean IS_BLOCKED=true;


    @Override
    public void save(UserDto userDto) {
        log.info("validating user");
        validateEmail(userDto.getEmail());
        validateDocumentNumberAndTramitNumber(userDto);
        log.info("user is valid");
        User user = UserMapper.instance.mapUserDtoToUser(userDto);
        user.setRole(Role.USER);
        log.info("encoding password");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAttempts(ATTEMPTS);
        user.setIsBloqued(!IS_BLOCKED);
        userRepository.save(user);
        log.info("Saved user");
    }
    private void validateEmail(String email) {
        log.info("Validating email: {}", email);
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists");
        });
    }
    private void validateDocumentNumberAndTramitNumber(UserDto userDto){
        log.info("Validating document number and tramit number");
        userRepository.findByTramitNumberAndDocumentNumber(userDto.getTramitNumber(), userDto.getDocumentNumber()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists");
        });
    }
    
    @Override
    public void resetPassword(String newPassword, String email) throws InvalidPasswordException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        String oldPassword = user.getPassword();
        boolean isSamePassword = passwordEncoder.matches(newPassword, oldPassword);
        if(isSamePassword){
            throw new InvalidPasswordException("The password cannot be the same as the previous one");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setAttempts(ATTEMPTS);
        user.setIsBloqued(!IS_BLOCKED);
        userRepository.save(user);
    }


    @Override
    public UserDto getUser(String email) {
        log.info("Getting user by email: {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("Got user");
        return UserMapper.instance.mapEntityToUserDto(user);
    }

    @Override
    public String getEmail(RecoveryRequestDto recoveryRequestDto) {
        String email=null;
        Optional<User> user =userRepository.findByTramitNumberAndDocumentNumber(recoveryRequestDto.getTramitNumber(), recoveryRequestDto.getDocumentNumber());
        if(user.isPresent()){
            email = user.get().getEmail();
        }
        return email;
    }




}
