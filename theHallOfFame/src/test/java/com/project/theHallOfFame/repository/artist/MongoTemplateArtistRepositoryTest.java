package com.project.theHallOfFame.repository.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.jupiter.api.Assertions.*;

class MongoTemplateArtistRepositoryTest {

    private final String COLLECTION_NAME = "artist";
    private final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/admin"));

@Test
void MongoDB_연결_테스트() {
    Query query = Query.query(Criteria.where("name").is("BTS"));
    Artist artist = mongoTemplate.findOne(query,Artist.class, COLLECTION_NAME);
    Assertions.assertThat(artist).isNotNull();
}
}
