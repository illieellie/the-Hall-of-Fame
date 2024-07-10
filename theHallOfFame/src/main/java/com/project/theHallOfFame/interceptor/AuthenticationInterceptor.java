package com.project.theHallOfFame.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.annotation.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    // private final 토큰에서 유저정보를 가져올 서비스
   // private final ObjectMapper objectMapper; //자바 객체를 json으로 serialization

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        System.out.println("[Debug] 첫번째 인터셉터 실행 ");
        boolean annotationCheck = checkControllerAnnotation(handler, Auth.class);
        if(!annotationCheck){
            return true; // 메소드에 에노테이션 설정된 것이 없으면 인가가 필요없기 때문에 pass
        }
        // 서비스 추가 : 토큰이 유효한지 검사 후 멤버 아이디를 요청으로 부터 가져오는 부분
        // request의 setAttribute로 넣어줌

        // 임시로 여기에서 검증 (token이 없으면 인가 불가)
         String token = request.getHeader("TOKEN");

        if(token==null){
            response.setStatus(HttpStatus.FORBIDDEN.value()); // 추후에 에러메세지도 함께 전달하기
            return false;
        }
        // token이 없으면 인가하면 안 됨
        // token이 있다는거 부터가 일반 멤버라는 뜻

        return true;
    }

    private boolean checkControllerAnnotation(Object handler, Class<Auth> auth) {
     // 이 구문을 넣어도 되는건지 테스트

        if(!(handler instanceof HandlerMethod)){
           return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(auth)!=null){
             // 해당 어노테이션이 존재하면
            // 어노테이션 자체가 없는지 확인하는지 보기
           return true;
        }
        return false;
    }

}
