package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {
  @Autowired
  PlaylistService playlistService;

  @Autowired
  SongService songService;

  @ModelAttribute("songs")
  public List<Song> songs() {
    return songService.findAll();
  }
}
