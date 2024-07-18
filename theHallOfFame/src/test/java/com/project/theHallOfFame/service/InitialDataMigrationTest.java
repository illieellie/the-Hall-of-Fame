package com.project.theHallOfFame.service;

import org.springframework.dao.DuplicateKeyException;
import com.project.theHallOfFame.domain.artist.Artist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InitialDataMigrationTest {

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Autowired
//    InitialDataMigration artistApi;
//
//    private final int SUCCESS_CODE = 200;
//
//    // 6HvZYsbFfjnjFrWF950C9d
//    List<String> artistList = new ArrayList<>();
//
//    InitialDataMigrationTest() {
//        artistList.clear();
//        artistList.add("6HvZYsbFfjnjFrWF950C9d"); // 뉴진스
//    }
//
//    @Test
//    public void API_호출_테스트() {
//
//        ResponseEntity<Map> responseEntity = artistApi.getArtist("3Nrfpe0tUJi4K4DXYWgMUX"); // test artist id
//
//        int httpStatus = responseEntity.getStatusCode().value();
//
//        // 200 예상
//        Assertions.assertThat(httpStatus).isEqualTo(200);
//
//    }
//
//    @Test
//    public void API_호출_후_DB_Insert() {
//        // 호출
//        ResponseEntity<Map> responseEntity = artistApi.getArtist(artistList.get(0)); // test artist id
//
//        int httpStatus = responseEntity.getStatusCode().value();
//        // 호출에 성공했으면
//        if (httpStatus == SUCCESS_CODE) {
//            // duplication 방지 -> artist id index 설정
//            try {
//                artistApi.insertDatabase(Objects.requireNonNull(responseEntity.getBody()));
//                Query query = Query.query(Criteria.where("artistId").is(artistList.get(0)));
//
//                Artist findArtist = mongoTemplate.findOne(query, Artist.class, "artist");
//                Assertions.assertThat(findArtist).isNotNull(); // 데이터 insert 후 데이터가 있으면 ok
//                System.out.println("[Insert] artist : " + findArtist.getName() + " 가 추가되었습니다.");
//            } catch (DuplicateKeyException e) {
//                System.out.println("[Error] 중복된 Key 입니다.");
//            }
//
//        }
//
//
//    }


}
