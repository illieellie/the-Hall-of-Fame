package com.project.theHallOfFame.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.annotation.Auth;
import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserSecurity;
import com.project.theHallOfFame.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.project.theHallOfFame.annotation.Auth.Role.MEMBER;

@RestController
@RequiredArgsConstructor
public class UserController {

    /* login 기능 */
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserSecurity user){
        // 인터셉터 테스트를 위해 우선 간단한 방식의 로그인을 구현, 디테일은 추후 수정

        // RequestBody 무조건 객체 맵핑 형식으로만 받아올수있는지 확인

        // 서비스에서 validation
        String token = userService.loginValidation(user.getUserId(), user.getUserPassword());
        if(token==null) {
            // 로그인이 인증되지 않은 것
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(token);
    }

    /* join 기능 */

    /* 로그 아웃 기능 */

    /* use page 기능 */
    @Auth(role=MEMBER)
    @GetMapping("/userPage/{userId}")
    public ResponseEntity<String> findUserPage(@PathVariable String userId) throws Exception {
        // 인터셉터로 해당 페이지 이동하기 전 인가

        // 이미 인터셉터에서 응답에 대한 바디를 지정했는데 여기서 바디를 추가로 지정하면 json이 안 예쁘게 추가되는 것 같음

        // user Page에 대한 정보들 불러오기

        UserDetails userDetails = userService.getUserDetails(userId);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDetails);

        Map<String, Map<String,String>> result = new HashMap<>();
        Map temp = objectMapper.convertValue(userDetails, Map.class);
        result.put("UserDetail", temp);
        json= objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);

        return ResponseEntity.ok()
                .header(HttpHeaders.DATE)
                .body(json);
    }


    /* admin page 기능 */
}
