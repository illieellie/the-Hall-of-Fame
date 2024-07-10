package com.project.theHallOfFame;

import com.project.theHallOfFame.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

   // private final AuthenticationInterceptor authInterceptor;

    // 문제점. 테스트에서는 RequiredArgsConstructor 가 먹히지 않는다
   @Bean
    public AuthenticationInterceptor authInterceptor(){
        return new AuthenticationInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor());
    }
}
