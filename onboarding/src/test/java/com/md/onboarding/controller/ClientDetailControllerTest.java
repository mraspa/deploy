package com.md.onboarding.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.dto.ValidateMailRequest;
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
import static com.md.onboarding.JUtils.Data.givenIdentityDocument;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ClientDetailController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ClientDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDetailService clientDetailService;

    @MockBean
    private MailConfirmationTokenService mailConfirmationTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCandidate_IsCreated_ReturnsCreated() throws Exception {

        ResultActions response = mockMvc.perform(post("/onboarding/v1/client-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenClientDetailRequest())));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkIdNumber_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkIdNumber(1234L)).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/check-id/1234"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getIdentityDocumentByIdNumber_IsOk_ReturnsEntity() throws Exception {
        when(clientDetailService.getIdentityDocumentByIdNumber(any())).thenReturn(givenIdentityDocument());

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/id-details/1234"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("genderType", CoreMatchers.is(givenIdentityDocument().getGenderType().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getIdentityByIdNumber_ThrowsNotFound() throws Exception {
        when(clientDetailService.getIdentityDocumentByIdNumber(any())).thenThrow(EntityNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/id-details/1234"));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void checkMail_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkMail("mail_test@gmail.com")).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/check-mail/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkPhoneNumber_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkPhoneNumber("1234-5678910")).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/check-phoneNumber/1234-5678910"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void checkCuil_IsOk_ReturnsBoolean() throws Exception {
        when(clientDetailService.checkCuilNumber(1122334455L)).thenReturn(false);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/check-cuil/1122334455"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getMaritalStatusListReturnsList() throws Exception {
        when(clientDetailService.getMaritalStatusStrings()).thenReturn(getMaritalStatusList());

        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/enums/marital-status"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getMaritalStatusList().size())));
    }

    @Test
    void getGenderTypeListReturnsList() throws Exception {
        when(clientDetailService.getGenderTypeStrings()).thenReturn(getGenderTypeList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/enums/gender-types"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getGenderTypeList().size())));
    }

    @Test
    void getidentityDocumentCopyListReturnsList() throws Exception {
        when(clientDetailService.getIdentityDocumentCopyTypeStrings()).thenReturn(getIdentityDocumentCopyList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/enums/identity-document-copies"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getIdentityDocumentCopyList().size())));
    }

    @Test
    void getJobTypeListReturnsList() throws Exception {
        when(clientDetailService.getJobTypeStrings()).thenReturn(getJobTypeList());
        ResultActions response = mockMvc.perform(get("/onboarding/v1/client-details/enums/job-types"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(getJobTypeList().size())));
    }

    @Test
    void sendMailConfirmationToken_IsOk_ReturnsBoolean() throws Exception{
        when(mailConfirmationTokenService.sendMailConfirmationToken("mail_test@gmail.com")).thenReturn(true);

        ResultActions response = mockMvc.perform(post("/onboarding/v1/client-details/sendMailConfirmationToken/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    void validateMailConfirmationToken_IsOk_ReturnsBoolean() throws Exception{
        ValidateMailRequest request = new ValidateMailRequest("mail_test@gmail.com",123456);

        when(mailConfirmationTokenService.validateMailConfirmationToken(request)).thenReturn(true);

        ResultActions response = mockMvc
                .perform(post("/onboarding/v1/client-details/validateMailConfirmationToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void cancelMailConfirmationTokenValidation_IsCancel_ReturnsOk() throws Exception {
        ResultActions response = mockMvc.perform(post("/onboarding/v1/client-details/cancelMailConfirmationTokenValidation/mail_test@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    
}