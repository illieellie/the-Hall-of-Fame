package com.project.theHallOfFame.security;

import org.junit.jupiter.api.*;
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

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    static String jwtToken; // 1시간 만료이므로 단위 테스트 시 토큰 재발급 필요

    Map<String, String> userInfo;
    @BeforeEach
    void setUp() {
        // Springboot 로 테스트하는 것이 아니여서 @Value를 가져오는데 에러 발생, 단위 테스트를 위해 reflection 설정
        ReflectionTestUtils.setField(jwtService, "jwtSecretKey", "TEMPORARY_ACCESS_KEY");
     }

    JwtServiceTest(){
        userInfo = new HashMap<>();
        userInfo.put("userId", "user1");
        userInfo.put("name", "최호랑"); // 한글도 영어도 ok
        userInfo.put("authority", "ADMIN");

    }

    @Test
    @Order(1)
    void createJwt() {
        String jwt = jwtService.createJwt(userInfo.get("userId"), userInfo.get("name"), userInfo.get("authority"));
        assertThat(jwt).isNotNull();
        jwtToken = jwt; // static 키워드를 넣지않으면 초기화 되어서 다음 테스트 메소드에서 값 활용이 안 됨
        System.out.println(jwt);
    }

    @Test
    @Order(2)
    void validationToken() {
        Map<String, String> result = jwtService.validationToken(jwtToken);
        assertThat(result).isNotNull();
        assertThat(result.get("name")).isEqualTo(userInfo.get("name"));

    }


}
