package com.project.theHallOfFame.repository.artist;


import com.project.theHallOfFame.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // private final 필드 생성자 주입
public class MongoTemplateArtistRepository implements ArtistRepository{

    private final MongoTemplate mongoTemplate;
    private final String COLLECTION_NAME = "artist";

    @Override
    public Artist findArtistById(String artistId) {
        /* 기능 삭제 예정 */
        Query query = Query.query(Criteria.where("artistId").is(artistId));
        return mongoTemplate.findOne(query,Artist.class, COLLECTION_NAME);
    }

    @Override
    public List<Artist> findAll() {
        return mongoTemplate.findAll(Artist.class, COLLECTION_NAME);
    }

    @Override
    public Artist findArtistByName(String artistName) {
        Query query = Query.query(Criteria.where("name").is(artistName));
        return mongoTemplate.findOne(query,Artist.class, COLLECTION_NAME);
    }

}
