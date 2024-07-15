package com.project.theHallOfFame.repository.user;

import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.repository.artist.MongoTemplateArtistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoTemplateUserRepositoryTest {

    final String COLLECTION_NAME = "userAccount";

    @Autowired
    MongoTemplateUserRepository userRepository;

    String userId;
    String userPw;
    MongoTemplateUserRepositoryTest(){
        userId = "sol4666";
        userPw = "1234";
    }

    @Test
    void getUserExist() {
        boolean isExistUser = userRepository.getUserExist(userId);
        Assertions.assertThat(isExistUser).isEqualTo(true);
    }

    @Test
    void getUserPw() {
        String getUserPw = userRepository.getUserPw(userId);
        Assertions.assertThat(getUserPw).isEqualTo(userPw);
    }

    @Test
    void getUserDetails() {
        UserDetails userDetails = userRepository.getUserDetails(userId);
        Assertions.assertThat(userDetails).isNotNull();
    }
}
