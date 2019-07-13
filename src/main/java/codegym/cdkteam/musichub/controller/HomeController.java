package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
  @Autowired
  SongDTOService songService;

  @Autowired
  PlaylistService playlistService;

  @GetMapping({"/" , "/home"})
  public ModelAndView showIndex(){
    // Playlist moi nhat
    List<Playlist> newestPlaylists = playlistService.findAllByOrderByCreatedAtDesc();
    // Playlist nhieu luot click nhat
    List<Playlist> mostPlayedPlaylists = playlistService.findTop6ByOrderByListenDesc();
    // Bai hat moi nhat
    List<SongDTO> newestSongs = songService.findAllByOrderByCreatedAtDesc();
    // Bai hat nhieu luot nghe nhat
    List<SongDTO> mostPlayedSongs = songService.findTop6ByOrderByListenDesc();

    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("newestPlaylists", newestPlaylists);
    modelAndView.addObject("mostPlayedPlaylists", mostPlayedPlaylists);
    modelAndView.addObject("newestSongs", newestSongs);
    modelAndView.addObject("mostPlayedSongs", mostPlayedSongs);
    return modelAndView;
  }

}
