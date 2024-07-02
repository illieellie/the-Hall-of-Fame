package com.project.theHallOfFame.controller.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.outputView.OutputView;
import com.project.theHallOfFame.service.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/artist/{artisId}")
    public Artist findArtistbyId(@PathVariable String artistId) {
        Artist artist = artistService.findArtistById(artistId);
        if (artist == null) {
            OutputView.dataNotFound(artistId, "findArtistbyId");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return artist;
    }

    @GetMapping("/artist/search/{artistName}")
    public Artist findArtistbyName(@PathVariable String artistName) {
        Artist artist = artistService.findArtistByName(artistName);
        if (artist == null) {
            OutputView.dataNotFound(artistName, "findArtistbyName");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return artist;
    }


    @GetMapping("/artist")
    public List<Artist> findArtistAll() {

        List<Artist> artistList = artistService.findArtistAll();
        if (artistList == null) {
            OutputView.dataNotFound("artist list", "findArtistAll");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return artistList;
    }


}
