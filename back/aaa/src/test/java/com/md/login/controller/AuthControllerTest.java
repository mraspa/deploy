package com.md.login.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.login.exception.UserAlreadyExistsException;
import com.md.login.exception.UserNotFoundException;
import com.md.login.jwt.JwtAuthenticationFilter;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.dto.UserDto;
import com.md.login.service.AuthService;
import com.md.login.service.UserService;
import com.md.login.utils.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;




    @Test
    void saveTest() throws Exception {
        doNothing().when(userService).save(any());
        ResultActions response = mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestEntityFactory.getUserDto())));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    void saveTestThrowsUserAlreadyExistsException() throws Exception {
        UserDto userDto = TestEntityFactory.getUserDto();
        doThrow(UserAlreadyExistsException.class).when(userService).save(Mockito.any(UserDto.class));

        ResultActions response = mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void login() throws Exception {

        ResultActions response = mockMvc.perform(post("/v1/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(TestEntityFactory.getUserDto())));

        response.andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());

    }
    @Test
    void loginThrowsUserNotFoundException() throws Exception {
        doThrow(UserNotFoundException.class).when(authService).login(Mockito.any(LoginRequestDto.class));

        ResultActions response = mockMvc.perform(post("/v1/auth/login")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(TestEntityFactory.getUserDto())));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
             .andDo(MockMvcResultHandlers.print());
    }
}
