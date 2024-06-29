package com.project.theHallOfFame;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.repository.artist.MongoTemplateArtistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoDBConfigTest {

    final String COLLECTION_NAME = "artist";
    //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoConfig.class,MongoDBConfig.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${mongodb.connectionString}")
    private String connectionString;

    @Test
    void mongoDB_다이렉트_연결_테스트() {
        final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(connectionString));

        Query query = Query.query(Criteria.where("name").is("BTS"));
        Artist artist = mongoTemplate.findOne(query, Artist.class, COLLECTION_NAME);
        Assertions.assertThat(artist).isNotNull();
    }

    @Test
    void mongoDB_Bean_주입_연결_테스트() {
        // 자동 주입 방식
        Query query = Query.query(Criteria.where("name").is("BTS"));
        Artist artist = mongoTemplate.findOne(query, Artist.class, COLLECTION_NAME);
        Assertions.assertThat(artist).isNotNull();
    }



}
