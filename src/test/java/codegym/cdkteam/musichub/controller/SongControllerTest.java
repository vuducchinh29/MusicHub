package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class SongControllerTest {
  private MockMvc mockMvc;

//  @InjectMocks
//  private SongController songController;
//
//  @Before
//  public void setUp() {
//    MockitoAnnotations.initMocks(this);
//    mockMvc = MockMvcBuilders.standaloneSetup(songController).build();
//  }

  @Test
  public void testAccessCreateTestSongPage() throws Exception {
    mockMvc
            .perform(get("/song"))
            .andExpect(status().isOk())
            .andExpect(view().name("song/create"));
  }
}
