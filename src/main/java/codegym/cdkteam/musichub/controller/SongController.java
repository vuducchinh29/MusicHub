package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.SongDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SongController {
  @Autowired
  SongDTOService songService;

  @Autowired
  UserDTOService userService;

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
    SongDTO song = songService.findById(id).get();
    ModelAndView modelAndView = new ModelAndView("song/details");
    modelAndView.addObject("song", song);
      return modelAndView;
  }

  @GetMapping("/play/{id}")
  public ModelAndView playMusic(@PathVariable long id){
    SongDTO song = songService.findById(id).get();
    ModelAndView modelAndView = new ModelAndView("song/play");
    modelAndView.addObject("song", song);
    return modelAndView;
  }
  @GetMapping("/list")
  public ModelAndView showAllSong(@RequestParam("song") Optional<String> song,
                                  @PageableDefault(value = 5) Pageable pageable) {
    Page<SongDTO> songs;
    if (song.isPresent()){
        songs = songService.findAllByNameContaining(song.get(), pageable);
    }
    else {
        songs = songService.findAllByOrderByCreatedAtDesc(pageable);
    }
    ModelAndView modelAndView = new ModelAndView("song/list");
    modelAndView.addObject("songs", songs);
    return modelAndView;
  }
  @GetMapping("/edit-song/{id}")
  public ModelAndView showEditSong(@PathVariable Long id){
    Song song = songService.findByIdWithTagIsString(id);
    ModelAndView modelAndView = new ModelAndView("song/editsong");
    modelAndView.addObject("song", song);
    return modelAndView;
  }
  @PostMapping("/edit-song")
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
