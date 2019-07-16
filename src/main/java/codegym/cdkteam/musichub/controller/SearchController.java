package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.SingerDTO;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SingerService;
import codegym.cdkteam.musichub.service.SongDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
  @Autowired
  SongDTOService songService;

  @Autowired
  PlaylistService playlistService;

  @Autowired
  SingerService singerService;

  @GetMapping("/search")
  public String search (@RequestParam(defaultValue = "song") String type, @RequestParam(defaultValue = "") String query, Model model) {
    if (type.equals("song")) {
      List<SongDTO> songs = songService.findAllByNameContaining(query);
      model.addAttribute("result", songs);
      return "search/song";
    } else if (type.equals("playlist")) {
      List<Playlist> playlists = playlistService.findAllByNameContaining(query);
      model.addAttribute("result", playlists);
      return "search/playlist";
    } else if (type.equals("singer")) {
      List<SingerDTO> singers = singerService.findAllByNameContaining(query);
      model.addAttribute("result", singers);
      return "search/singer";
    } else {
      return "404";
    }
  }
}
