package com.project.theHallOfFame.repository.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;


@SpringBootTest
class MongoTemplateArtistRepositoryTest {

    final String COLLECTION_NAME = "artist";

    @Autowired
    MongoTemplateArtistRepository artistRepository;


    @Test
    void findArtistById() {
        Artist artist = artistRepository.findArtistById("3Nrfpe0tUJi4K4DXYWgMUX");
        Assertions.assertThat(artist.getName()).isEqualTo("BTS");
    }


    @Test
    void findAll() {
        List<Artist> artistList = artistRepository.findAll();
        Assertions.assertThat(artistList).isNotNull();
    }

    @Test
    void findArtistByName() {
        Artist artist = artistRepository.findArtistByName("BLACKPINK");
        Assertions.assertThat(artist.getName()).isEqualTo("BLACKPINK");
    }

    // 데이터 없을 경우
    @Test
    void findArtistById_NULL() {
        Artist artist = artistRepository.findArtistById("1234");
        Assertions.assertThat(artist).isEqualTo(null);
    }


}
