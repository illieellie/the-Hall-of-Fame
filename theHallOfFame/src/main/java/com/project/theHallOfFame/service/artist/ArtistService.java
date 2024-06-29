package com.project.theHallOfFame.service.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.repository.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist findArtist(String name) {
        return artistRepository.findArtistByName(name);
    }

    public List<Artist> findArtistAll() {
        return artistRepository.findAll();
    }
}
