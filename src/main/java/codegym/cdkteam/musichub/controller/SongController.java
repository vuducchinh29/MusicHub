package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.SongDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/songs")
public class SongController {
  @Autowired
  SongDTOService songService;

  @Autowired
  UserDTOService userService;

  @GetMapping("/add")
  public ModelAndView addNewSong() {
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    modelAndView.addObject("song", new Song());
    return modelAndView;
  }

  @PostMapping("/add")
  public ModelAndView createSong(@Validated @ModelAttribute("song") Song song, BindingResult bindingResult, Principal principal) {
    if (bindingResult.hasFieldErrors()){
      ModelAndView modelAndView = new ModelAndView("song/addNewSong");
      return modelAndView;
    }
    song.setOwner(userService.findByEmail(principal.getName()));
    songService.save(song);
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    modelAndView.addObject("song", new Song());
    modelAndView.addObject("message", "Upload success!");
    return modelAndView;
  }

  @GetMapping("/{id}")
  public ModelAndView showSongDetail(@PathVariable long id){
    Optional<SongDTO> song = songService.findById(id);
    ModelAndView modelAndView;
    if (song.isPresent()) {
      modelAndView = new ModelAndView("song/details");
      modelAndView.addObject("song", song.get());
    } else {
      modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }

  @GetMapping("/play/{id}")
  public ModelAndView playMusic(@PathVariable long id){
    SongDTO song = songService.findById(id).get();
    ModelAndView modelAndView = new ModelAndView("song/play");
    modelAndView.addObject("song", song);
    return modelAndView;
  }

  @GetMapping
  public ModelAndView showAllSong(Principal principal) {
    UserDTO user = userService.findByEmail(principal.getName());
    Set<SongDTO> songs = user.getUploadedSongs();
    ModelAndView modelAndView = new ModelAndView("song/list");
    modelAndView.addObject("songs", songs);
    return modelAndView;
  }

  @GetMapping("/update/{id}")
  public ModelAndView showEditSong(@PathVariable Long id){
    Song song = songService.findByIdWithTagIsString(id);
    ModelAndView modelAndView;
    if (song != null) {
      modelAndView = new ModelAndView("song/editsong");
      modelAndView.addObject("song", song);
    } else {
      modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }
  @PostMapping("/update")
    public String updateSong(@ModelAttribute("song") Song song, RedirectAttributes redirect){
      songService.save(song);
      redirect.addFlashAttribute("message", "The song has been updated");
      return "redirect:/list";
  }
  @GetMapping("/delete/{id}")
  public  ModelAndView showDelete(@PathVariable Long id, RedirectAttributes redirectAttributes){
    songService.delete(id);
    ModelAndView modelAndView = new ModelAndView("redirect:/list");
    redirectAttributes.addFlashAttribute("message", "The song has been delete");
    return modelAndView;
  }

  @GetMapping("/like/{id}")
  @ResponseBody
  public String likeSong (@PathVariable Long id, Principal principal) {
    Optional<SongDTO> song = songService.findById(id);
    if (song.isPresent() && principal != null) {
      int like = songService.like(song.get(), userService.findByEmail(principal.getName()));
      return Integer.toString(like);
    } else if (principal == null) {
      return "no login";
    } else {
      return "not found";
    }
  }
}
