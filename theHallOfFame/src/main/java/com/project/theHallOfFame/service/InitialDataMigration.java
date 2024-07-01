package com.project.theHallOfFame.service;

import com.project.theHallOfFame.api.Spotify;
import com.project.theHallOfFame.domain.artist.Artist;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InitialDataMigration {

    private final MongoTemplate mongoTemplate;

    Spotify spotify = new Spotify();
    String spotifyToken = spotify.accessToken();

    public ResponseEntity<Map> getArtist(String id) {

        //id는 spotify 아티스트 고유 id
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + spotifyToken);
        ;
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<Map> responseEntity = rest.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, requestEntity, Map.class);

        String response = responseEntity.getBody().toString();
        System.out.println(response);

        return responseEntity;
        //return response;
    }

    // map 파싱 후 insert
    public void insertDatabase(Map map) {
        // 파싱
        ArrayList imagesList = (ArrayList) map.get("images");
        LinkedHashMap LinkedHash = (LinkedHashMap) imagesList.get(0);
        String imagesUrl = LinkedHash.get("url").toString();

        LinkedHashMap LinkedHash2 = (LinkedHashMap) map.get("external_urls");
        String href = LinkedHash2.get("spotify").toString();

        Artist artist = new Artist();
        artist.setName(map.get("name").toString());
        artist.setArtistId(map.get("id").toString());
        artist.setHref(href);
        artist.setImages(imagesUrl);

        mongoTemplate.insert(artist, "artist");
    }


}
