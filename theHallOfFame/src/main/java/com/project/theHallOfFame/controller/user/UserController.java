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

        // + 이미 로그인된 사용자라면? (토큰이 있다면)

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
