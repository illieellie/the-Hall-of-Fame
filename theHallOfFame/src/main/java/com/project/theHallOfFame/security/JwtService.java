package com.project.theHallOfFame.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private final SignatureAlgorithm jwtAlgorithm;

    private final int expiredSetting;

    private final Date expiredTime;

    public JwtService(){
        expiredSetting = 60*60*10000; // 1hour
        jwtAlgorithm = SignatureAlgorithm.HS256;
        Date time = new Date();
        expiredTime = new Date(time.getTime()+expiredSetting);
    }

    public String createJwt(String userId, String userName, String authority){
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setHeaderParam("alg",jwtAlgorithm)
                .claim("userId",userId)
                .claim("name", userName)
                .claim("authority", authority)
                .setExpiration(expiredTime)
                .signWith(jwtAlgorithm, jwtSecretKey)
                .compact();
    }

    // 토큰 가져오기
    private String getJwtToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

     public Map<String, String> validationToken() {
        Map<String, String> userInfo = new HashMap<>();
        //1. JWT 추출
        String accessToken = getJwtToken();
        if(accessToken == null || accessToken.isEmpty()){
            System.out.println("[Debug] 토큰을 찾을 수 없습니다.");
            return userInfo;
        }

        // 2. JWT parsing
        Jws<Claims> claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            System.out.println("[Debug] 토큰이 유효하지 않습니다.");
            return userInfo;
        }

        // 3. userId, 권한 추출

        userInfo.put("userId", claims.getBody().get("userId",String.class));
        userInfo.put("name", claims.getBody().get("name",String.class));
        userInfo.put("authority", claims.getBody().get("authority",String.class));
        userInfo.put("token", accessToken);

        return userInfo;
    }






}
