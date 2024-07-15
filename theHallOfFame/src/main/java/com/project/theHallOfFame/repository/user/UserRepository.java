package com.project.theHallOfFame.repository.user;

import com.project.theHallOfFame.domain.user.UserDetails;

import java.util.Map;

public interface UserRepository {

    boolean getUserExist(String id);

    String getUserPw(String id);

    UserDetails getUserDetails(String userId);
}
