package com.md.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.service.ClientDetailService;
import com.md.onboarding.service.MailConfirmationTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = MailConfirmationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MailConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDetailService clientDetailService;

    @MockBean
    private MailConfirmationTokenService mailConfirmationTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendMailConfirmationToken_IsOk_ReturnsBoolean() throws Exception{
        when(mailConfirmationTokenService.sendMailConfirmationToken("mail_test@gmail.com")).thenReturn(true);

        ResultActions response = mockMvc.perform(post("/onboarding/v1/mails/sendMailConfirmationToken/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    void validateMailConfirmationToken_IsOk_ReturnsBoolean() throws Exception{
        ValidateMailRequest request = new ValidateMailRequest("mail_test@gmail.com",123456);

        when(mailConfirmationTokenService.validateMailConfirmationToken(request)).thenReturn(true);

        ResultActions response = mockMvc
                .perform(post("/onboarding/v1/mails/validateMailConfirmationToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void cancelMailConfirmationTokenValidation_IsCancel_ReturnsOk() throws Exception {
        ResultActions response = mockMvc.perform(post("/onboarding/v1/mails/cancelMailConfirmationTokenValidation/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
