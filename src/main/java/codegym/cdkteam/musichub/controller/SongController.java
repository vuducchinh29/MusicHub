package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SongController {
  @Autowired
  SongService songService;

  @GetMapping("/add-new-song")
  public ModelAndView addNewSong() {
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    modelAndView.addObject("song", new Song());
    return modelAndView;
  }

  @PostMapping("/add-new-song")
  public ModelAndView createSong(@Validated @ModelAttribute("song") Song song, BindingResult bindingResult) {
    if (bindingResult.hasFieldErrors()){
      ModelAndView modelAndView = new ModelAndView("song/addNewSong");
      return modelAndView;
    }
    songService.save(song);
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    modelAndView.addObject("song", new Song());
    modelAndView.addObject("message", "Upload success!");
    return modelAndView;
  }

  @GetMapping("/song-detail/{id}")
  public ModelAndView showSongDetail(@PathVariable long id){
    Song song = songService.findById(id).get();
    ModelAndView modelAndView = new ModelAndView("song/details");
    modelAndView.addObject("song", song);
    return modelAndView;
  }

  @GetMapping("/play/{id}")
  public ModelAndView playMusic(@PathVariable long id){
    Song song = songService.findById(id).get();
    ModelAndView modelAndView = new ModelAndView("song/play");
    modelAndView.addObject("song", song);
    return modelAndView;
  }
//  @GetMapping("/")
//  public ModelAndView showAllSong() {
//    List<Song> songs = songService.findAll();
//    ModelAndView modelAndView = new ModelAndView("song/index");
//    modelAndView.addObject("songs", songs);
//    return modelAndView;
//  }
}
