package com.md.login.jwt;


import com.md.login.utils.TestEntityFactory;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;



import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        userDetails = TestEntityFactory.getUser();
    }

    @Test
    void getToken() {

        String token = jwtService.getToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromToken() {
        String token = jwtService.getToken(userDetails);

        String username = jwtService.getUsernameFromToken(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void isTokenValid() {
        String token = jwtService.getToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void getClaim() {

        String token = jwtService.getToken(userDetails);
        String expectedSubject = userDetails.getUsername();
        String subject = jwtService.getClaim(token, Claims::getSubject);

        assertEquals(expectedSubject, subject);
    }


}