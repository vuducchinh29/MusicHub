package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

  @GetMapping("/create")
  public ModelAndView createNewPlaylist() {
    ModelAndView modelAndView = new ModelAndView("playlist/addNewPlaylist");
    modelAndView.addObject("playlist", new Playlist());
    return modelAndView;
  }
}
