package com.project.theHallOfFame.repository.user;

import java.util.Map;

public interface UserRepository {

    boolean getUserExist(String id);

    String getUserPw(String id);
}
