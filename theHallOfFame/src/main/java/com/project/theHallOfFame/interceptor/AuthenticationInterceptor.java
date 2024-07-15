package com.project.theHallOfFame.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.annotation.Auth;
import com.project.theHallOfFame.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    // private final 토큰에서 유저정보를 가져올 서비스
   // private final ObjectMapper objectMapper; //자바 객체를 json으로 serialization

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        System.out.println("[Debug] 권한 검증 인터셉터 실행");

        if(!(handler instanceof HandlerMethod)){
            return true; // 찾아보고 이해
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Auth annotation = getControllerAnnotation(handlerMethod);


        if(annotation == null){
            return true; // 메소드에 에노테이션 설정된 것이 없으면 pass
        }

        // String token = request.getHeader("TOKEN");
        // 토큰 유효성 검사 후 userId 반환 
        Map<String, String> userInfo = jwtService.validationToken();

        if(userInfo.isEmpty()){
            response.setStatus(HttpStatus.FORBIDDEN.value()); // 추후에 에러메세지도 함께 전달하기
            System.out.println("[Debug] 토큰이 없습니다. or 토큰이 유효하지 않습니다. 로그인 페이지로 이동합니다.");
            response.encodeRedirectURL("/login");
            return false;
        }

        request.setAttribute("userId", userInfo.get("userId"));
        request.setAttribute("name", userInfo.get("name"));
        // request.setAttribute("token", userInfo.get("token"));

        response.setContentType("text/html;charset=UTF-8");

        Map<String, Map<String,String>> result = new HashMap<>();
        result.put("Auth", userInfo);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        response.getWriter().write(json);

        // 유효하면 애노테이션이 admin 인지 검사해서 회원이 admin 영역에 포함되어 있는지 확인, admin 요청이 아니면 패스
        if(annotation.role().compareTo(Auth.Role.ADMIN)==0){
            if(!userInfo.get("authority").equals("ADMIN")){
                // admin 영역에 포함안되어있으면 권한이 불충분하다고 메세지 넣어서 보내기
                response.setStatus(HttpStatus.FORBIDDEN.value());

                System.out.println("[Debug] admin 권한이 필요한 요청입니다. 권한이 불충분합니다.");
                return false;
            }
        }

        return true;
    }

    private Auth getControllerAnnotation(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Auth.class);
    }


}
