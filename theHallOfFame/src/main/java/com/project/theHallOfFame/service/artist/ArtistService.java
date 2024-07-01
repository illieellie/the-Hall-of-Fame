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

    public Artist findArtistById(String artistId) {
        return artistRepository.findArtistById(artistId);
    }

    public List<Artist> findArtistAll() {
        return artistRepository.findAll();
    }

    public Artist findArtistByName(String artistName) {return artistRepository.findArtistByName(artistName);
    }
}
