package com.project.theHallOfFame.service.spotify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpotifyServiceTest {

    @Autowired
    SpotifyService spotifyService;

    private final int SUCCESS_CODE = 200;

    List<String> artistList = new ArrayList<>();

    SpotifyServiceTest() {
        //artistList.clear();
        artistList.add("6HvZYsbFfjnjFrWF950C9d"); // 뉴진스
    }


    @Test
    void getArtist() {
        ResponseEntity<Map> responseEntity = spotifyService.getArtist("3Nrfpe0tUJi4K4DXYWgMUX"); // test artist id

        int httpStatus = responseEntity.getStatusCode().value();

        // 200 예상
        Assertions.assertThat(httpStatus).isEqualTo(200);
    }


    @Test
    void insertArtist() throws Exception {
        //spotifyService.insertArtist("3Nrfpe0tUJi4K4DXYWgMUX");
        spotifyService.insertArtist("41MozSoPIsD1dJM0CLPjZF");
    }
}
