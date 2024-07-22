package com.project.theHallOfFame.service.spotify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.api.Spotify;
import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.repository.spotify.MongoTemplateSpotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final MongoTemplateSpotifyRepository spotifyRepository;
    private final Spotify spotify;
    private final ObjectMapper objectMapper;

    String spotifyToken;

    public ResponseEntity<Map> getArtist(String id) {

        spotifyToken = spotify.getAccessToken();
        //id : spotify 아티스트 고유 id
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + spotifyToken);
        ;
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<Map> responseEntity = rest.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, requestEntity, Map.class);

        Map response = responseEntity.getBody();
        System.out.println(response);

        return responseEntity;
    }

    public void insertArtist(String id) throws Exception {
        ResponseEntity<Map> responseEntity = getArtist(id);
        Map response = responseEntity.getBody();

        // 이미 있는 지 확인 후 insert, 이미 있다면 집어넣지 않기

        Artist artist = new Artist();
        String temp = null;

        // 파싱
        temp = response.get("id").toString();

        // 이미 db에 있는 지 확인
        boolean isExistArtist = spotifyRepository.isExistArtist(temp);
        if (isExistArtist) {
            System.out.println("이미 DB에 존재하는 Artist 입니다. id = " + temp);
            return;
        }

        artist.setArtistId(temp);
        temp = response.get("name").toString();
        artist.setName(temp);

        ArrayList imagesList = (ArrayList) response.get("images");
        LinkedHashMap LinkedHash = (LinkedHashMap) imagesList.get(0);
        String imagesUrl = LinkedHash.get("url").toString();

        LinkedHashMap LinkedHash2 = (LinkedHashMap) response.get("external_urls");
        String href = LinkedHash2.get("spotify").toString();

        artist.setHref(href);
        artist.setImages(imagesUrl);

//        temp = response.get("external_urls");
//        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp);
//        artist.setHref(json);
//
//        temp = response.get("images");
//        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp);
//        artist.setImages(json);

        spotifyRepository.insertArtist(artist);

        System.out.println("DB에 저장 완료.");
    }


}
