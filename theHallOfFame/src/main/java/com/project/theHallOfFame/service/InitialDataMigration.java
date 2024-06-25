package com.project.theHallOfFame.service;

import com.project.theHallOfFame.api.Spotify;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class InitialDataMigration {

    Spotify spotify = new Spotify();
    String spotifyToken = spotify.accessToken();

    public ResponseEntity<String> getArtist(String id){

        //id는 spotify 아티스트 고유 id
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + spotifyToken);;
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, requestEntity, String.class);

        String response = responseEntity.getBody();
        System.out.println(response);

        return responseEntity;
        //return response;
    }
}
