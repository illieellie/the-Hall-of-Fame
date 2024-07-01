package com.project.theHallOfFame.controller.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.service.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    // 찾았는데 없는 경우도 생각하기

    @GetMapping("/artist/{artisId}")
    public Artist findArtistbyId(@PathVariable String artistId) {

        return artistService.findArtistById(artistId);
    }

    @GetMapping("/artist/search/{artistName}")
    public Artist findArtistbyName(@PathVariable String artistName) {

        return artistService.findArtistByName(artistName);
    }


    @GetMapping("/artist")
    public List<Artist> findArtistAll() {

        return artistService.findArtistAll();
    }


}
