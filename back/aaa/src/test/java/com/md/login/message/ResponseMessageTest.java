package com.md.login.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseMessageTest {

    @Test
    void testResponseMessage() {
        ResponseMessage responseMessage = new ResponseMessage.ResponseMessageBuilder()
                .message("Hello World!")
                .build();

        String message = responseMessage.getMessage();

        assertEquals("Hello World!", message);
    }
    @Test
    void testNoArgsConstructor() {
        ResponseMessage responseMessage = new ResponseMessage();

        assertNotNull(responseMessage);
    }

    @Test
    void testAllArgsConstructor() {
        String message = "Test message";

        ResponseMessage responseMessage = new ResponseMessage(message);

        assertNotNull(responseMessage);
        assertEquals(message, responseMessage.getMessage());
    }

    @Test
    void testSetter() {
        ResponseMessage responseMessage = new ResponseMessage();
        String newMessage = "New test message";

        responseMessage.setMessage(newMessage);

        assertEquals(newMessage, responseMessage.getMessage());
    }
}