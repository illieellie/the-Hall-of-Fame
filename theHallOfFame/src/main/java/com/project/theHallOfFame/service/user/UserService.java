package com.project.theHallOfFame.service.user;

import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.repository.user.UserRepository;
import com.project.theHallOfFame.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String loginValidation(String id, String pw){

        String token = null;

        // id 가 있는지 먼저 검증
        String repoPw = null;
        if(userRepository.getUserExist(id)){
           repoPw = userRepository.getUserPw(id);  // 디비에서 id, pw 가져오기
        }
        if(checkMatch(pw, repoPw)){ // get token
            token = createToken(id);
        }
        return token;
    }

    private boolean checkMatch(String pw, String repoPw) {
    if(pw.equals(repoPw)){
        return true;
    }   // 테스트 해볼것
        return false;
    }


    private String createToken(String id) {
        // JWT 토큰 발급

       //userRepository.에서 세부정보 가져오기 (db 설계부터 하자..)


        //jwtService.createJwt();
        /*
        * String userId,
    String userName,
    String isAdmin
        * */
        //jwtService.createJwt();
        return "1";
    }


    public UserDetails getUserDetails(String userId) {
        return userRepository.getUserDetails(userId);
    }
}
