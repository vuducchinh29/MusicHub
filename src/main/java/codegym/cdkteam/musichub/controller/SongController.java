package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SongController {
  @Autowired
  SongService songService;

  @GetMapping("/add-new-song")
  public ModelAndView addStaff() {
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    modelAndView.addObject("song", new Song());
    return modelAndView;
  }
}
