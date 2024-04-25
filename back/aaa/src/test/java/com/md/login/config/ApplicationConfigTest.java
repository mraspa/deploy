package com.md.login.config;

import com.md.login.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ApplicationConfigTest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testBeans() {
        assertNotNull(authenticationManager);
        assertNotNull(authenticationProvider);
        assertNotNull(passwordEncoder);
        assertNotNull(userRepository);
    }
}