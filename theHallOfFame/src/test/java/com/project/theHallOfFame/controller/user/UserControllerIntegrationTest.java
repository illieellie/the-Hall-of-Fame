package com.project.theHallOfFame.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserJoinInput;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    private UserJoinInput userJoinInput;
    //private String jwtToken;

    UserControllerIntegrationTest(){
        userJoinInput = new UserJoinInput();
        userJoinInput.setUserId("test_user");
        userJoinInput.setUserPassword("test1234");
        userJoinInput.setEmail("test_mail@test.com");
        userJoinInput.setName("username");
        userJoinInput.setPhone("010-0000-0000");
        userJoinInput.setAuthority("MEMBER");
    }


    @Test
    @Order(1)
    @DisplayName("로그인성공_토큰발급")
    void loginUser_success() throws Exception{

        mockMvc.perform(
                        post("/login")
                                .content("{\"userId\":\"test_user\", \"userPassword\":\"test1234\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcRestDocumentation.document("user/integration/loginUser_success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(print());

        //this.jwtToken
    }

    @Test
    @DisplayName("로그인실패_토큰미발급")
    void loginUser_fail() throws Exception{

        mockMvc.perform(
                        post("/login")
                                .content("{\"userId\":\"userId\", \"userPassword\":\"1235\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcRestDocumentation.document("user/integration/loginUser_fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void getUserHomePage_success() throws Exception{

        mockMvc.perform(
                        get("/userPage/{userId}", "test_user")
                                .header("Authorization", "Bearer " + "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOiJ0ZXN0X3VzZXIiLCJuYW1lIjoidXNlcm5hbWUiLCJhdXRob3JpdHkiOiJNRU1CRVIiLCJleHAiOjE3MjE4NzI2MTd9.clyxpKmJD1O6l-FDNx4ebZ_wTPiPnGn8PwfUfzszItQ")
                ).andDo(MockMvcRestDocumentation.document("user/integration/getUserHomePage_success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                //.andExpect(content().string(containsString("ACCEPT")))
                .andDo(print());

    }

    @Test
    void getUserHomePage_fail() throws Exception{
        mockMvc.perform(
                        get("/userPage/{userId}", "sol4666")
                                .header("Tok", "Tok1234") // 유효하지 않은 토큰
                ).andDo(MockMvcRestDocumentation.document("user/integration/getUserHomePage_fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


    @Test
    void getUserJoinForm() throws Exception{

        mockMvc.perform(
                        get("/join")
                ).andDo(MockMvcRestDocumentation.document("user/integration/getUserJoinForm",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("MEMBER")))
                .andDo(print());
    }

//    // user join 시 필드 validation - 입력 필드가 유효하지 않은 경우 등 검증
    @Test
    void saveUserJoinForm_BeanValidation_성공() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJoinInput);
        mockMvc.perform(
                        post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andDo(MockMvcRestDocumentation.document("user/integration/saveUserJoinForm_BeanValidation_success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                //.andExpect(content().string(containsString("MEMBER")))
                .andDo(print());
        // db insert 까지 되는 작업
    }

    @Test
    void saveUserJoinForm_BeanValidation_실패_휴대폰번호() throws Exception{


        userJoinInput.setPhone("01000000000");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJoinInput);
        mockMvc.perform(
                        post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().is4xxClientError())
                //.andExpect(content().string(containsString("MEMBER")))
                .andDo(print());
    }

    @Test
    void saveUserJoinForm_BeanValidation_실패_두개이상() throws Exception{


        userJoinInput.setUserId(null);
        userJoinInput.setPhone("01000000000");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJoinInput);
        mockMvc.perform(
                        post("/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andDo(MockMvcRestDocumentation.document("user/integration/saveUserJoinForm_BeanValidation_fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


}
