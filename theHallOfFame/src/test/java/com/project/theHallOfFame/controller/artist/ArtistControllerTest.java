package com.project.theHallOfFame.controller.artist;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.service.artist.ArtistService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(ArtistController.class)
class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistService artistService;

    Artist artist;
    Artist artist2;
    List<Artist> artistList;

    Map<String, Object> artistAlbum;

    public ArtistControllerTest() {

        artist = new Artist();
        artist2 = new Artist();
        artistList = new ArrayList<>();
        artistList.add(artist);
        artistList.add(artist2);

        artist.setName("BTS");
        artist.setArtistId("3Nrfpe0tUJi4K4DXYWgMUX");
        artist.setHref("https://open.spotify.com/artist/3Nrfpe0tUJi4K4DXYWgMUX");
        artist.setImages("https://i.scdn.co/image/ab6761610000e5ebd642648235ebf3460d2d1f6a");

        artist2.setName("BLACKPINK");
        artist2.setArtistId("41MozSoPIsD1dJM0CLPjZF");
        artist2.setHref("https://open.spotify.com/artist/41MozSoPIsD1dJM0CLPjZF");
        artist2.setImages("https://i.scdn.co/image/ab6761610000e5ebc9690bc711d04b3d4fd4b87c");


        artistAlbum = new HashMap<>();
        artistAlbum.put("total", 20);
        artistAlbum.put("items", "items");

    }

    @Test
    void findArtistById() throws Exception {
        String artistId = artist.getArtistId();

        // mockMvc 행위 지정
        doReturn(artistAlbum).when(artistService).findArtistById(artistId);

        mockMvc.perform(
                        get("/artist/{id}", artistId)
                                //.param("artistId", artistId)
                ).andDo(MockMvcRestDocumentation.document("artist/findArtistById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(print());
                //.andExpect(content().string(containsString("BTS")));
    }


//    @Test
//    void findArtistbyName() throws Exception {
//        String artistName = artist.getName();
//
//        // mockMvc 행위 지정
//        doReturn(artist).when(artistService).findArtistByName(artistName);
//
//        mockMvc.perform(
//                        get("/artist/search/{artistName}", artistName)
//                                //.param("artistName", artistName)
//                ).andDo(MockMvcRestDocumentation.document("artist/findArtistByName",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString(artist.getArtistId())));
//
//    }

    @Test
    void findArtistAll() throws Exception {

        // mockMvc 행위 지정
        doReturn(artistList).when(artistService).findArtistAll();

        mockMvc.perform(
                        get("/artist")
                ).andDo(MockMvcRestDocumentation.document("artist/findArtistAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
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
