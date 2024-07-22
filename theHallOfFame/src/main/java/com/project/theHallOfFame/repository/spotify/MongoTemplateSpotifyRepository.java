package com.project.theHallOfFame.repository.spotify;

import com.project.theHallOfFame.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MongoTemplateSpotifyRepository {

    private final MongoTemplate mongoTemplate;
    private final String COLLECTION_NAME = "artist";

    public void insertArtist(Artist artist) {
    mongoTemplate.insert(artist, COLLECTION_NAME);
    }

    public boolean isExistArtist(String id) {
        Query query = Query.query(Criteria.where("artistId").is(id));
        return mongoTemplate.exists(query, COLLECTION_NAME);
    }
}
