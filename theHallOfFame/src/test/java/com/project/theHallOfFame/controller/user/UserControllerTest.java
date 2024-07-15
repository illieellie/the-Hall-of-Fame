package com.project.theHallOfFame.controller.user;

import com.project.theHallOfFame.WebConfig;
import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.interceptor.AuthenticationInterceptor;
import com.project.theHallOfFame.security.JwtService;
import com.project.theHallOfFame.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class, WebConfig.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthenticationInterceptor authInterceptor;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;


    Map<String, String> userInfo;
    UserDetails userDetails;
    UserControllerTest(){
        userInfo = new HashMap<>();
        userInfo.put("userId", "user1");
        userInfo.put("name", "최호랑");
        userInfo.put("token", "!@#$%^");

        userDetails = new UserDetails();
        userDetails.setUserId("user1");
        userDetails.setAuthority("ADMIN");
    }



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

    @Test
    void findUserPage_success() throws Exception{

        // 진짜 토큰을 보내야 통과

        // mockMvc 행위 지정
        doReturn(userInfo).when(jwtService).validationToken();


        mockMvc.perform(
                        get("/userPage/{userId}", "1")
                                .param("userId", userInfo.get("userId"))
                                .header("Authorization", "temp-token")
                ).andExpect(status().isOk())
                //.andExpect(content().string(containsString("ACCEPT")))
                .andDo(print());
    }

    @Test
    void findUserPage_fail() throws Exception{
        mockMvc.perform(
                        get("/userPage/{userId}", "1")
                                .param("userId", "1")
                                .header("Tok", "Tok1234") // 대소문자 상관이 없음
                ).andExpect(status().is4xxClientError())
                .andDo(print());
    }
    @Test
    void findUserPageDetail_success() throws Exception{

        // 토큰을 보내야 통과
        // mockMvc 행위 지정
        doReturn(userInfo).when(jwtService).validationToken();
        doReturn(userDetails).when(userService).getUserDetails("user1");

        mockMvc.perform(
                        get("/userPage/{userId}", "user1")
                                .header("Authorization", "temp-token")
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("ADMIN")))
                .andDo(print());
    }

}
