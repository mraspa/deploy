package com.md.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.service.ClientDetailService;
import com.md.onboarding.service.MailConfirmationTokenService;
import org.hamcrest.CoreMatchers;
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

import static com.md.onboarding.JUtils.Data.getGenderTypeList;
import static com.md.onboarding.JUtils.Data.getIdentityDocumentCopyList;
import static com.md.onboarding.JUtils.Data.getJobTypeList;
import static com.md.onboarding.JUtils.Data.getMaritalStatusList;
import static com.md.onboarding.JUtils.Data.givenClientDetailRequest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = OnboardingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OnboardingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDetailService clientDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MailConfirmationTokenService mailConfirmationTokenService;

    @Test
    void createCandidate_IsCreated_ReturnsCreated() throws Exception {

        ResultActions response = mockMvc.perform(post("/onboarding/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenClientDetailRequest())));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkIdNumber_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkIdNumber(1234L)).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/check-id/1234"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkMail_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkMail("mail_test@gmail.com")).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/check-mail/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkPhoneNumber_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkPhoneNumber("1234-5678910")).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/check-phoneNumber/1234-5678910"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkCuil_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkCuilNumber("1122334455")).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/check-cuil/1122334455"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getMaritalStatusListReturnsList() throws Exception {
        when(clientDetailService.getMaritalStatusStrings()).thenReturn(getMaritalStatusList());

        ResultActions response = mockMvc.perform(get("/onboarding/v1/enums/marital-status"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getMaritalStatusList().size())));
    }

    @Test
    void getGenderTypeListReturnsList() throws Exception {
        when(clientDetailService.getGenderTypeStrings()).thenReturn(getGenderTypeList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/enums/gender-types"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getGenderTypeList().size())));
    }

    @Test
    void getidentityDocumentCopyListReturnsList() throws Exception {
        when(clientDetailService.getIdentityDocumentCopyTypeStrings()).thenReturn(getIdentityDocumentCopyList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/enums/identity-document-copies"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getIdentityDocumentCopyList().size())));
    }

    @Test
    void getJobTypeListReturnsList() throws Exception {
        when(clientDetailService.getJobTypeStrings()).thenReturn(getJobTypeList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/enums/job-types"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getJobTypeList().size())));
    }

}
