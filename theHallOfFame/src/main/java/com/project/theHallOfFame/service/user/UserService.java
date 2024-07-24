package com.project.theHallOfFame.service.user;

import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserJoinInput;
import com.project.theHallOfFame.domain.user.UserSecurity;
import com.project.theHallOfFame.repository.user.UserRepository;
import com.project.theHallOfFame.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String loginValidation(String id, String pw){

        String token = null;

        // id 가 있는지 먼저 검증
        UserSecurity userSecurity;
        if(userRepository.getUserExist(id)){
           userSecurity = userRepository.getUserSecurity(id);  // 디비에서 유저 시큐리티 get

            if(checkMatch(pw, userSecurity.getUserPassword())){ // get token
                token = createToken(userSecurity);
            }
        }

        return token;
    }

    private boolean checkMatch(String pw, String repoPw) {
    if(pw.equals(repoPw)){
        return true;
    }   // 테스트 해볼것
        return false;
    }


    private String createToken(UserSecurity us) {
        // JWT 토큰 발급
        return jwtService.createJwt(us.getUserId(), us.getName(), us.getAuthority());
    }


    public UserDetails getUserDetails(String userId) {
        return userRepository.getUserDetails(userId);
    }

    public void userJoinInputSave(UserJoinInput userJoinInput) throws Exception{
        // 아이디 중복이 있는지 확인
        String userId = userJoinInput.getUserId();
        if(!userRepository.getUserExist(userId)){
            // 에러 반환
            throw new Exception("Error : 이미 동일한 ID가 있습니다.");
        }
        userRepository.saveUserAccount(userJoinInput);


    }
}
