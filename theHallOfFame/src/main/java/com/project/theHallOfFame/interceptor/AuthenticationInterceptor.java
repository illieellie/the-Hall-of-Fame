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

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final static String NON_EXIST_TOKEN = "토큰이 존재하지 않습니다. 로그인 페이지로 이동합니다.";
    private final static String NON_VALIDATION_TOKEN = "유효하지 않은 토큰입니다. 로그인 페이지로 이동합니다.";
    private final static String NON_AUTHORIZED_REQUEST = "admin 권한이 필요한 요청입니다. 권한이 불충분합니다.";
    private final int BEARER_STR_LENGTH = 7;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        System.out.println("[Debug] 권한 검증 인터셉터 실행");

        if(!(handler instanceof HandlerMethod)){
            return true; // 찾아보고 이해
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // annotation 검사
        Auth annotation = getControllerAnnotation(handlerMethod);

        if(annotation == null){
            return true;
        }

        // get token
        String accessToken = request.getHeader("Authorization");

        // 토큰 유무 확인
        if(accessToken ==null||accessToken.isEmpty()||accessToken.length()<BEARER_STR_LENGTH){
            System.out.println("[Debug] 토큰을 찾을 수 없습니다.");
            setErrorResponse(response, NON_EXIST_TOKEN);
            return false;
        }
        accessToken = accessToken.substring(BEARER_STR_LENGTH);

        // 토큰 유효성 검사 후 userId 반환
        Map<String, String> userInfo = jwtService.validationToken(accessToken);

        if(userInfo.isEmpty()){
            setErrorResponse(response, NON_VALIDATION_TOKEN);
            return false;
        }

        request.setAttribute("name", userInfo.get("name"));
        response.setContentType("text/html;charset=UTF-8"); // 한글 호환 적용

        response.getWriter().write(makeResponseJson(userInfo));

        // 유효하면 애노테이션이 admin 인지 검사해서 회원이 admin 영역에 포함되어 있는지 확인, admin 요청이 아니면 패스
        if(!checkAdminAuthority(annotation, userInfo)){
            setErrorResponse(response, NON_AUTHORIZED_REQUEST);
            return false;
        }

        return true;
    }

    private boolean checkAdminAuthority(Auth annotation, Map<String, String> userInfo){
        if(annotation.role().compareTo(Auth.Role.ADMIN)==0){
            if(!userInfo.get("authority").equals("ADMIN")){
                return false;
            }
        }
        return true;
    }

    private String makeResponseJson(Map<String, String> userInfo) throws Exception{
        Map<String, Map<String,String>> result = new HashMap<>();
        result.put("Auth", userInfo);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        return json;
    }

    private Auth getControllerAnnotation(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Auth.class);
    }

    private void setErrorResponse(HttpServletResponse response, String ms) throws Exception{
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.sendError(HttpStatus.FORBIDDEN.value(), ms);
        response.encodeRedirectURL("/login");
    }

}
