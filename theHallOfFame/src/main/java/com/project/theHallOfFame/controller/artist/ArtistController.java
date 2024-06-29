package com.project.theHallOfFame.controller.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.service.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/artist/{id}")
    public Artist findArtist(){
        Artist member = artistService.findArtist("BTS");

        // 추후에 json 형식으로 반환 예정
        return member;
    }

    @GetMapping("/artist")
    public List<Artist> findArtistAll(){
        List<Artist> member = artistService.findArtistAll();

        // 추후에 json 형식으로 반환 예정
        return member;
    }



}
