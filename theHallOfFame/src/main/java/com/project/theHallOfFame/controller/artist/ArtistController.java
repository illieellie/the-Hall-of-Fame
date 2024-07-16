package com.project.theHallOfFame.controller.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.outputView.OutputView;
import com.project.theHallOfFame.service.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ObjectMapper objectMapper;

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<String> findArtistbyId(@PathVariable String artistId) throws Exception{
        Artist artist = artistService.findArtistById(artistId);
        if (artist == null) {
            OutputView.dataNotFound(artistId, "findArtistbyId");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        /* json 생성하는 중복되는 코드 개선하기 */
        Map<String,Artist > result = new HashMap<>();
        result.put("artist", artist);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        return ResponseEntity.ok().body(json);
    }

    @GetMapping("/artist/search/{artistName}")
    public ResponseEntity<String> findArtistbyName(@PathVariable String artistName) throws Exception{
        Artist artist = artistService.findArtistByName(artistName);
        if (artist == null) {
            OutputView.dataNotFound(artistName, "findArtistbyName");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Map<String,Artist > result = new HashMap<>();
        result.put("artist", artist);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        return ResponseEntity.ok().body(json);
    }

    @GetMapping("/artist")
    public ResponseEntity<String> findArtistAll() throws Exception{

        List<Artist> artistList = artistService.findArtistAll();
        if (artistList == null) {
            OutputView.dataNotFound("artist list", "findArtistAll");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Map<String,List<Artist>> result = new HashMap<>();
        result.put("artistList", artistList);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);

        return ResponseEntity.ok().body(json);
    }





}
