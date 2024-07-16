package com.project.theHallOfFame.repository.user;

import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserSecurity;

import java.util.Map;

public interface UserRepository {

    boolean getUserExist(String id);

    UserDetails getUserDetails(String userId);

    UserSecurity getUserSecurity(String userId);
}
