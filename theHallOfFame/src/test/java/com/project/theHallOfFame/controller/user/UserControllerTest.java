package com.project.theHallOfFame.controller.user;

import com.project.theHallOfFame.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @Test
    @DisplayName("로그인성공_토큰발급")
    void login_success() throws Exception{
        String userId = "userId";
        String userPw = "1234";
        // mockMvc 행위 지정
        doReturn("Token1").when(userService).loginValidation(userId, userPw);

        mockMvc.perform(
                        post("/login")
                                .content("{\"userId\":\"userId\", \"userPassword\":\"1234\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("Token1")))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인실패_토큰미발급")
    void login_fail() throws Exception{
        String userId = "userId";
        String userPw = "1234";
        // mockMvc 행위 지정
        doReturn("Token1").when(userService).loginValidation(userId, userPw);

        mockMvc.perform(
                        post("/login")
                                .content("{\"userId\":\"userId\", \"userPassword\":\"1235\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
