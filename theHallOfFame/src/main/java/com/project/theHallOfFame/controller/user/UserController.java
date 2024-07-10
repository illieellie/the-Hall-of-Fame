package com.project.theHallOfFame.controller.user;

import com.project.theHallOfFame.annotation.Auth;
import com.project.theHallOfFame.domain.user.User;
import com.project.theHallOfFame.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.theHallOfFame.annotation.Auth.Role.MEMBER;

@RestController
@RequiredArgsConstructor
public class UserController {
    // 서비스 의존성 주입
    // /login 기능

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        // 인터셉터 테스트를 위해 우선 간단한 방식의 로그인을 구현, 디테일은 추후 수정

        // 서비스에서 validation
        String token = userService.loginValidation(user.getUserId(), user.getUserPassword());
        if(token==null) {
            // 로그인이 인증되지 않은 것
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(token);
    }

    // /join 기능
    // 로그 아웃 기능

    // userpage에 연결
    @Auth(role=MEMBER)
    @GetMapping("/userPage/{userId}")
    public ResponseEntity<String> findUserPage(@PathVariable String userId){
        // 인터셉터로 해당 페이지 이동하기 전 인가
        System.out.println("[Debug] 테스트 로그입니다. findUserPage 컨트롤러.");

        // userPage에 대한 정보들 불러오기
        return ResponseEntity.ok().body("ACCEPT");
    }

    // admin page 연결?
}
