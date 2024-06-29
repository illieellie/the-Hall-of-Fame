package com.project.theHallOfFame.repository.artist;

//import com.project.theHallOfFame.AutoConfig;

import com.project.theHallOfFame.AutoConfig;
import com.project.theHallOfFame.MongoDBConfig;
import com.project.theHallOfFame.domain.artist.Artist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoTemplateArtistRepositoryTest {

    final String COLLECTION_NAME = "artist";

    @Autowired
    MongoTemplateArtistRepository artistRepository;


    @Test
    void findArtistByName() {
        Artist artist = artistRepository.findArtistByName("BTS");
        Assertions.assertThat(artist.getName()).isEqualTo("BTS");
    }


    @Test
    void findAll() {
        List<Artist> artistList = artistRepository.findAll();
        Assertions.assertThat(artistList).isNotNull();
    }
}
