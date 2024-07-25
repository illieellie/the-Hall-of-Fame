package com.project.theHallOfFame.controller.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.annotation.Auth;
import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserJoinInput;
import com.project.theHallOfFame.domain.user.UserSecurity;
import com.project.theHallOfFame.service.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.theHallOfFame.annotation.Auth.Role.MEMBER;

@RestController
@RequiredArgsConstructor
public class UserController {

    /* login 기능 */
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserSecurity user) {

        // 서비스에서 validation
        String token = userService.loginValidation(user.getUserId(), user.getUserPassword());
        if (token == null) {
            // 로그인이 인증되지 않은 것
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(token);
    }


    @GetMapping("/join")
    public UserJoinInput getUserJoinForm() {
        UserJoinInput userJoinInput = new UserJoinInput();
        userJoinInput.setAuthority("MEMBER");
        return userJoinInput;
    }

    @PostMapping("/join")
    public ResponseEntity<String> saveUserJoinForm(@Validated @RequestBody UserJoinInput userJoinInput, BindingResult bindingResult) throws Exception {
        // validation
        if (bindingResult.hasErrors()) {
            // 메세지 : 유효하지 않는 입력 형식
            // 4xx 에러 반환

            String json = makeJson(bindingResult.toString());

            return ResponseEntity.badRequest()
                    .body(json);
            // 에러 메세지를 헤더에다가 넣는게 맞는지 찾아보기
        }
        // db insert (서비스에서 중복 여부 확인)
        try {
            userService.userJoinInputSave(userJoinInput);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok().body(null);
    }


    /* 로그 아웃 기능
    1차 : access token 만 사용하는 jwt 방식이므로 토큰 정보를 db에 저장하지 않기 때문에 서버측에서는 로그아웃 역할이 없음
    프론트에서 토큰 삭제 필요
    */

    /* use page 기능 */
    @Auth(role = MEMBER)
    @GetMapping("/userPage/{userId}")
    public ResponseEntity<String> getUserHomePage(@PathVariable String userId) throws Exception {
        // 인터셉터로 해당 페이지 이동하기 전 인가

        UserDetails userDetails = userService.getUserDetails(userId);

        Map<String, Map<String, String>> result = new HashMap<>();
        Map temp = objectMapper.convertValue(userDetails, Map.class);
        result.put("UserDetail", temp);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);

        return ResponseEntity.ok()
                .header(HttpHeaders.DATE)
                .body(json);
    }

    private String makeJson(String str) throws Exception {

        String[] split = str.split("\n");

        List<Map> errors = new ArrayList<>();
        // 에러 메세지 6개씩
        for(int i = 1; i< split.length; i++){
            String[] split1 = split[i].split(";");

            Map<String, String> map = new HashMap<>();
            map.put("cause", split1[0]);
            map.put("need format", split1[5]);
            errors.add(map);
        }

        Map<String, List<Map>> result = new HashMap<>();
        result.put("errors", errors);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }

    // 추후 개발

    /* account page와 개인 my page를 나눌까 생각중*/

    /* user page 정보 수정 */

    /* admin page 기능 */

}
