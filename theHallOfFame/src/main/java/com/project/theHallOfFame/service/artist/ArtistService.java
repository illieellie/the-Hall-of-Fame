package com.project.theHallOfFame.service.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.repository.artist.ArtistRepository;
import com.project.theHallOfFame.service.spotify.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SpotifyService spotifyService;

    public Map findArtistById(String artistId) {

        // db에서 가져오는 것이 아닌 api 호출로 세부정보 가져오기
        Map artistAlbums = spotifyService.getArtistAlbums(artistId);

        return artistAlbums;
    }

    public List<Artist> findArtistAll() {
        return artistRepository.findAll();
    }

    public Artist findArtistByName(String artistName) {
        // * 추후에 이름으로 찾는 부분은 서비스 추가 개발 필요 (대소문자 관련)
        // 어떻게 할지 비즈니스로직 회의 필요
        return artistRepository.findArtistByName(artistName);
    }
}
