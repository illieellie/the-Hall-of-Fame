package com.project.theHallOfFame.domain.user;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class UserDetails {

    @Id
    ObjectId id; // mongoTemplate 고유 id

    String userId;
    String authority;
    String email;
    List<String> likeArtist;
    List<String> likeMusic;
    String name;
    String phone;
    String notes;
}
