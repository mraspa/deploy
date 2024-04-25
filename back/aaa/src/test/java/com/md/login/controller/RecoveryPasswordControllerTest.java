package com.md.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.md.login.jwt.JwtAuthenticationFilter;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import com.md.login.service.EmailService;
import com.md.login.service.RecoveryCodeService;
import com.md.login.service.UserService;
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

import static com.md.login.utils.TestEntityFactory.getMaskedMailDto;
import static com.md.login.utils.TestEntityFactory.getRecoveryRequestDto;
import static com.md.login.utils.TestEntityFactory.getResetPasswordDto;
import static com.md.login.utils.TestEntityFactory.getValidateCodeDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecoveryPasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RecoveryPasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecoveryCodeService recoveryCodeService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void passwordRecoveryTest() throws Exception {

        RecoveryRequestDto requestDto = getRecoveryRequestDto();
        MaskedMailDto maskedMailDto = getMaskedMailDto();

        when(emailService.getMaskedEmail(any(RecoveryRequestDto.class))).thenReturn(maskedMailDto);

        mockMvc.perform(post("/v1/password/recovery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(maskedMailDto)))
                .andDo(MockMvcResultHandlers.print());



    }

    @Test
    void validateCodeTest() throws Exception {


        ValidateCodeDto validateCodeDto = getValidateCodeDto();
        doNothing().when(recoveryCodeService).validateCode(any(ValidateCodeDto.class));


        ResultActions response = mockMvc.perform(post("/v1/password/validate-code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validateCodeDto)));

        response.andExpect(status().isOk());
    }

    @Test
    void passwordResetTest() throws Exception {

        ResetPaswordDto resetPaswordDto = getResetPasswordDto();

        ResultActions response = mockMvc.perform(patch("/v1/password/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPaswordDto)));

        response.andExpect(status().isOk());
    }


}