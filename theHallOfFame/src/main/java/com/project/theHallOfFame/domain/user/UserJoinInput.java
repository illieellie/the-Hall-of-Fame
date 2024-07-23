package com.project.theHallOfFame.domain.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class UserJoinInput {
    @Id
    private ObjectId id; // mongoTemplate 고유 id

    @NotBlank
    private String userId;

    @Min(8)
    private String userPassword;

    @NotBlank
    private String name;
    private String authority;
    private String email;
    private String phone;
}
