package com.project.theHallOfFame.service.user;

import com.project.theHallOfFame.WebConfig;
import com.project.theHallOfFame.controller.user.UserController;
import com.project.theHallOfFame.domain.user.UserSecurity;
import com.project.theHallOfFame.repository.user.UserRepository;
import com.project.theHallOfFame.security.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;


    String id;
    String pw;
    String authority;
    String name;
    UserSecurity us;

    UserServiceTest(){
        id = "aa";
        pw = "1234";
        authority = "ADMIN";
        name = "J";

        us = new UserSecurity();
        us.setUserId(id);
        us.setName(name);
        us.setAuthority(authority);
        us.setUserPassword(pw);
    }


    @Test
    void loginValidation_pw_일치() {

        // mock
        doReturn(true).when(userRepository).getUserExist(id);
        doReturn(us).when(userRepository).getUserSecurity(id);
        doReturn("token4").when(jwtService).createJwt(id, name, authority);

        // 패스워드 매치 테스트

        String token = userService.loginValidation(id, pw);
        Assertions.assertThat(token).isNotNull();

    }

    @Test
    void loginValidation_pw_불일치() {

        // mock
        doReturn(true).when(userRepository).getUserExist(id);
        doReturn(us).when(userRepository).getUserSecurity(id);
        //doReturn("token4").when(jwtService).createJwt(id, name, authority);

        // 패스워드 매치되는지 정도 테스트 될듯
        String token = userService.loginValidation(id, "4444");
        Assertions.assertThat(token).isEqualTo(null);
    }


}
