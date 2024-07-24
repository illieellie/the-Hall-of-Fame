package com.project.theHallOfFame;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfig {

    @Value("${api.spotify.id}")
    private String CLIENT_ID;

    @Value("${api.spotify.pw}")
    private String CLIENT_SECRET;

    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();
    }
}
