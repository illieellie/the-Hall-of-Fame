package com.project.theHallOfFame.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class UserSecurity {
    @Id
    ObjectId id; // mongoTemplate 고유 id

    String userId;
    String userPassword;

}
