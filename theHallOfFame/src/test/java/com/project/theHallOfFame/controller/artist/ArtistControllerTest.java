package com.project.theHallOfFame.controller.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.service.artist.ArtistService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtistController.class)
class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistService artistService;

    Artist artist;
    Artist artist2;
    List<Artist> artistList;

    public ArtistControllerTest() {

        artist = new Artist();
        artist2 = new Artist();
        artistList = new ArrayList<>();
        artistList.add(artist);
        artistList.add(artist2);

        artist.setName("BTS");
        artist.setArtistId("abcd");

        artist2.setName("BLACKPINK");
        artist2.setArtistId("abcde");
    }

    @Test
    void findArtistbyId() throws Exception {
        String artistId = artist.getArtistId();

        // mockMvc 행위 지정
        doReturn(artist).when(artistService).findArtistById(artistId);

        mockMvc.perform(
                        get("/artist/{id}", artistId)
                                .param("artistId", artistId)
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("BTS")))
                .andDo(print());
    }

    @Test
    void findArtistbyName() throws Exception {
        String artistName = artist.getName();

        // mockMvc 행위 지정
        doReturn(artist).when(artistService).findArtistByName(artistName);

        mockMvc.perform(
                        get("/artist/search/{artistName}", artistName)
                                .param("artistName", artistName)
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("abcd")));

    }

    @Test
    void findArtistAll() throws Exception {

        // mockMvc 행위 지정
        doReturn(artistList).when(artistService).findArtistAll();

        mockMvc.perform(
                        get("/artist")
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("BLACKPINK")))
                .andDo(print());

    }

    @Test
    @DisplayName("데이터가 없는 경우")
    void findArtistbyId_null() throws Exception {
        String artistId = artist.getArtistId();

        // mockMvc 행위 지정
        doReturn(null).when(artistService).findArtistById(artistId);

        mockMvc.perform(
                        get("/artist/{id}", artistId)
                                .param("artistId", artistId)
                ).andExpect(status().is4xxClientError())
                .andDo(print());
    }

}
