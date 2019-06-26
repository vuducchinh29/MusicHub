package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.SongService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class SongControllerTest {
  private MockMvc mockMvc;
  private static String samplename;
  private static String sampledescription;

  @InjectMocks
  private SongController songController;

  @Autowired
  private SongService songService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(songController).build();
  }

  @Test
  public void testAccessCreateTestSongPage() throws Exception {
    mockMvc
        .perform(get("/song"))
        .andExpect(status().isOk())
        .andExpect(view().name("song/create"));
  }

  @Test
  public void testAddNewSong() throws Exception {
    doNothing()
        .when(songService)
        .save(isA(Song.class));
    mockMvc
        .perform(post("/song")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", samplename)
                .param("description", sampledescription))
            .andExpect(status().isFound())
            .andExpect(redirectedUrlPattern("/song/details/*"));
  }
}
