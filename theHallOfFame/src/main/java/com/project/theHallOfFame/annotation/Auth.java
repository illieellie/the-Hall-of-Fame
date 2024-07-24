package com.project.theHallOfFame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {

    Role role() default Role.NONE;

    enum Role{
        NONE, // 일반 유저
        MEMBER, // 회원 가입한 멤버
        ADMIN // 어드민
    }

}


