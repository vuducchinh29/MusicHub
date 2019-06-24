package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongController {
  @Autowired
  SongService songService;

  @GetMapping("/admin/add-new-song")
  public String addNewSong(Model model){
    model.addAttribute("song", new Song());
    return "/song/addNewSong";
  }
}
