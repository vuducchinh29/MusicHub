package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.SingerDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.SingerService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class SongController {
  @Autowired
  SongDTOService songService;

  @Autowired
  UserDTOService userService;

  @Autowired
  SingerService singerService;

  @GetMapping("/songs/add")
  public ModelAndView addNewSong() {
    ModelAndView modelAndView = new ModelAndView("song/addNewSong");
    List<SingerDTO> singers = singerService.findAll();
    modelAndView.addObject("song", new Song());
    modelAndView.addObject("singers", singers);
    return modelAndView;
  }

  @PostMapping("/songs/add")
  public ModelAndView createSong(@Validated @ModelAttribute("song") Song song, Principal principal, RedirectAttributes redirectAttributes) {
    song.setOwner(userService.findByEmail(principal.getName()));
    songService.save(song);
    ModelAndView modelAndView = new ModelAndView("redirect:/songs/add");
    redirectAttributes.addFlashAttribute("song", new Song());
    redirectAttributes.addFlashAttribute("message", "Upload success!");
    return modelAndView;
  }

  @GetMapping("/songs/{id}")
  public ModelAndView showSongDetail(@PathVariable long id){
    Optional<SongDTO> song = songService.findById(id);
    List<SongDTO> songs = songService.findTop5ByOrderByListenDesc();
    ModelAndView modelAndView;
    if (song.isPresent()) {
        modelAndView = new ModelAndView("song/details");
        modelAndView.addObject("song", song.get());
        modelAndView.addObject("songs", songs);
      } else {
        modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }

  @GetMapping("/songs")
  public ModelAndView showAllSong(Principal principal) {
    UserDTO user = userService.findByEmail(principal.getName());
    Set<SongDTO> songs = user.getUploadedSongs();
    ModelAndView modelAndView = new ModelAndView("song/songs");
    modelAndView.addObject("songs", songs);
    return modelAndView;
  }

  @GetMapping("/allsongs")
  public ModelAndView showAllSong() {
    List<SongDTO> songs = songService.findAll();
    ModelAndView modelAndView = new ModelAndView("song/allsongs");
    modelAndView.addObject("songs", songs);
    return modelAndView;
  }

  @GetMapping("/songs/update/{id}")
  public ModelAndView showEditSong(@PathVariable Long id, Principal principal){
    UserDTO user = userService.findByEmail(principal.getName());
    Optional<SongDTO> song = songService.findById(id);
    ModelAndView modelAndView;

    if (song != null) {
      if (song.get().getOwner() == user){
        List<SingerDTO> allSinger = singerService.findAll();
        List<SingerDTO> checkedSinger = song.get().getSingers();
        List<SingerDTO> uncheckSinger = songService.uncheckedSinger(allSinger,checkedSinger);
        modelAndView = new ModelAndView("song/editsong");
        modelAndView.addObject("song", song.get());
        modelAndView.addObject("allSinger", allSinger);
        modelAndView.addObject("checkedSinger", checkedSinger);
        modelAndView.addObject("uncheckSinger", uncheckSinger);
      } else {
        modelAndView = new ModelAndView("404");
      }
    } else {
      modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }

  @PostMapping("/songs/update")
    public String updateSong(@ModelAttribute("song") Song song, RedirectAttributes redirect){
      songService.save(song);
      redirect.addFlashAttribute("message", "The song has been updated");
      return "redirect:/songs/update/" + song.getId();
  }
  @GetMapping("/songs/delete/{id}")
  public  ModelAndView showDelete(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
    UserDTO user = userService.findByEmail(principal.getName());
    Optional<SongDTO> song = songService.findById(id);
    ModelAndView modelAndView;
    if (song.get().getOwner() == user) {
      songService.delete(id);
      modelAndView = new ModelAndView("redirect:/songs");
      redirectAttributes.addFlashAttribute("message", "The song has been delete");
    } else {
      modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }

  @GetMapping("/songs/like/{id}")
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
