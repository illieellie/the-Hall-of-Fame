package com.project.theHallOfFame.repository.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import java.util.List;

public interface ArtistRepository {
    //List<Artist> findAll();
    Artist findArtistById(String id);

    List<Artist> findAll();

    Artist findArtistByName(String artistName);
}
