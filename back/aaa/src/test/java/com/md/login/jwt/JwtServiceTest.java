package com.md.login.jwt;


import com.md.login.model.entity.User;
import com.md.login.utils.TestEntityFactory;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private JwtService jwtService;
    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        user = TestEntityFactory.getUser();
    }

    @Test
    void getToken() {

        String token = jwtService.getToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromToken() {
        String token = jwtService.getToken(user);

        String username = jwtService.getUsernameFromToken(token);

        assertEquals(user.getUsername(), username);
    }

    @Test
    void isTokenValid() {
        String token = jwtService.getToken(user);

        boolean isValid = jwtService.isTokenValid(token, user);

        assertTrue(isValid);
    }

    @Test
    void getClaim() {

        String token = jwtService.getToken(user);
        String expectedSubject = user.getUsername();
        String subject = jwtService.getClaim(token, Claims::getSubject);

        assertEquals(expectedSubject, subject);
    }


}