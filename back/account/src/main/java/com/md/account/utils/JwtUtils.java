package com.md.account.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.account.exception.InvalidJwtException;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class JwtUtils {
    private Map<String, Object> extractClaims(String jwtToken) {
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            throw new InvalidJwtException("Invalid token");
        }

        String jwt = jwtToken.substring(7);
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new InvalidJwtException("JWT token is not in the correct format");
        }

        String payload = new String(Base64.getDecoder().decode(parts[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException | JsonProcessingException e) {
            throw new InvalidJwtException("Error processing JWT token");
        }
    }

    public String getDocumentNumber(String jwt) {
        return (String) extractClaims(jwt).get("documentNumber");
    }
}
