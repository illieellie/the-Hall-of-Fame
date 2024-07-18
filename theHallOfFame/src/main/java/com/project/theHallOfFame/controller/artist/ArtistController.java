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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/artist/{artistId}")
    public Map<String, Object> findArtistbyId(@PathVariable String artistId) {
        Artist artist = artistService.findArtistById(artistId);
        if (artist == null) {
            OutputView.dataNotFound(artistId, "findArtistbyId");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return convertToMap("artist", artist);
    }

    @GetMapping("/artist/search/{artistName}")
    public Map<String, Object> findArtistbyName(@PathVariable String artistName) {
        Artist artist = artistService.findArtistByName(artistName);
        if (artist == null) {
            OutputView.dataNotFound(artistName, "findArtistbyName");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return convertToMap("artist", artist);
    }


    @GetMapping("/artist")
    public Map<String, Object> findArtistAll() {

        List<Artist> artistList = artistService.findArtistAll();
        if (artistList == null) {
            OutputView.dataNotFound("artist list", "findArtistAll");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return convertToMap("artistList", artistList);
    }


    private Map<String, Object> convertToMap(String name, Object obj){
        Map<String, Object> map = new HashMap<>();
        map.put(name,obj);
        return map;
    }

}
