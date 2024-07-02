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

    public Artist findArtistByName(String artistName) {
        // * 추후에 이름으로 찾는 부분은 서비스 추가 개발 필요 (대소문자 관련)
        // 어떻게 할지 비즈니스로직 회의 필요
        return artistRepository.findArtistByName(artistName);
    }
}
