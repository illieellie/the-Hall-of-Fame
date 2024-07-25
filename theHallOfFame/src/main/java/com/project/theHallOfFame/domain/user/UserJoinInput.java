package com.project.theHallOfFame.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class UserJoinInput {
//    @Id
//    private ObjectId id; // mongoTemplate 고유 id

    @NotBlank
    private String userId;

    private String userPassword;

    @NotBlank
    private String name;
    private String authority;

    @Email
    private String email;

    @Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}")
    private String phone;
}
