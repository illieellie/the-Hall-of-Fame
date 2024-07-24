package com.project.theHallOfFame.controller.artist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ArtistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findArtistbyId() throws Exception{
        mockMvc.perform(
                        get("/artist/{id}","3Nrfpe0tUJi4K4DXYWgMUX")
                ).andDo(MockMvcRestDocumentation.document("artist/integration/findArtistbyId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("BTS")))
                .andDo(print());
    }

    @Test
    void findArtistAll() throws Exception {
        mockMvc.perform(
                        get("/artist")
                ).andDo(MockMvcRestDocumentation.document("artist/integration/findArtistAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("BLACKPINK")))
                .andDo(print());
    }
}
