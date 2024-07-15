package com.project.theHallOfFame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.interceptor.AuthenticationInterceptor;
import com.project.theHallOfFame.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

   // private final AuthenticationInterceptor authInterceptor;

    // 문제점. 테스트에서는 RequiredArgsConstructor 가 먹히지 않는다
   @Bean
    public AuthenticationInterceptor authInterceptor(){
        return new AuthenticationInterceptor(jwtService(), objectMapper());
    }

    public ObjectMapper objectMapper(){
       return  new ObjectMapper();
    }

    @Bean
    public JwtService jwtService(){
        return new JwtService();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor());
    }
}
