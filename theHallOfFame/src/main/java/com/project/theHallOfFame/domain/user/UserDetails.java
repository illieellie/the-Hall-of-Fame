package com.project.theHallOfFame.domain.user;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class UserDetails {

    String userId;
    String authority;
    String phone;
    String email;
    List<String> likeArtist;
    List<String> likeMusic;
    String notes;
}
