package com.project.theHallOfFame.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

class InitialDataMigrationTest {
    @Test
    public void API_호출_테스트(){
        InitialDataMigration init = new InitialDataMigration();
        ResponseEntity<String> responseEntity = init.getArtist("0TnOYISbd1XYRBk9myaseg"); // test artist id

        int httpStatus = responseEntity.getStatusCode().value();

        // 200 예상
        Assertions.assertThat(httpStatus).isEqualTo(200);

    }

}
