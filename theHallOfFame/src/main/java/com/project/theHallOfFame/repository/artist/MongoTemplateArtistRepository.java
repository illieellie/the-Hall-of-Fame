package com.project.theHallOfFame.repository.artist;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;
import com.project.theHallOfFame.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // private final 필드 생성자 주입
public class MongoTemplateArtistRepository implements ArtistRepository{

    private final MongoTemplate mongoTemplate;
    private final String COLLECTION_NAME = "artist";

    @Override
    public Artist findArtistByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query,Artist.class, COLLECTION_NAME);
    }

    @Override
    public List<Artist> findAll() {
        return mongoTemplate.findAll(Artist.class, COLLECTION_NAME);
    }


}
