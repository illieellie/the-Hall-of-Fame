package com.project.theHallOfFame.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(Properties.class)
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    String tempJwtToken; // 1시간 만료이므로 단위 테스트 시 토큰 재발급 필요

    Map<String, String> userInfo;
    @BeforeEach
    void setUp() {
        // Springboot 로 테스트하는 것이 아니여서 @Value를 가져오는데 에러 발생, 단위 테스트를 위해 reflection 설정
        ReflectionTestUtils.setField(jwtService, "jwtSecretKey", "TEMPORARY_ACCESS_KEY");
        tempJwtToken = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOiJhYSIsIm5hbWUiOiJKIiwiYXV0aG9yaXR5IjoiQURNSU4iLCJleHAiOjE3MjExMjA2Mjd9.fNhtykTh4tqyJ4y8akpVBuqicBLIYXAW2zn6to5PRuA";
      }

    JwtServiceTest(){
        userInfo = new HashMap<>();
        userInfo.put("userId", "user1");
        userInfo.put("name", "최호랑");
        userInfo.put("token", "!@#$%^");

    }

    @Test
    void createJwt() {
        String userId = "aa";
        String userName = "J";
        String authority = "ADMIN";
        String jwt = jwtService.createJwt(userId, userName, authority);
        assertThat(jwt).isNotNull();
        System.out.println(jwt);
    }


    // validation token도 검증 완료, 메서드내 getJwtToken()은 클라이언트의 요청이 필요하므로 테스트 코드에서는 생략


}
