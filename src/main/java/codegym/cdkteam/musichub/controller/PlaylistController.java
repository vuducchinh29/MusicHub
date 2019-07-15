package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PlaylistController {
  @Autowired
  PlaylistService playlistService;

  @Autowired
  SongDTOService songService;

  @Autowired
  UserDTOService userService;

  @ModelAttribute("songs")
  public List<SongDTO> songs() {
    return songService.findAll();
  }

  @GetMapping("/playlists/add")
  public ModelAndView createNewPlaylist() {
    ModelAndView modelAndView = new ModelAndView("playlist/addNewPlaylist");
    modelAndView.addObject("playlist", new Playlist());
    return modelAndView;
  }

  @PostMapping("/playlists/add")
  public ModelAndView addNewPlaylist(@ModelAttribute("playlist") Playlist playlist, RedirectAttributes redirectAttributes, Principal principal) {
    playlist.setOwner(userService.findByEmail(principal.getName()));
    ModelAndView modelAndView = new ModelAndView("redirect:/playlists/add");
    convertIdToSong(playlist);
    redirectAttributes.addFlashAttribute("message", "Create playlist success!");
    return modelAndView;
  }

  private void convertIdToSong(@ModelAttribute("playlist") Playlist playlist) {
    String[] listupdateSongID = playlist.getSongIDs();
    List<SongDTO> updateListSong = new ArrayList<>();
    for (int i = 0; i < listupdateSongID.length; i++) {
      Long songID = Long.parseLong(listupdateSongID[i]);
      SongDTO song = songService.findById(songID).get();
      updateListSong.add(song);
    }
    playlist.setSongs(updateListSong);
    playlistService.save(playlist);
  }

  @GetMapping("/allplaylists")
  public ModelAndView allPlaylist(){
    List<Playlist> playlists = playlistService.findAll();
    ModelAndView modelAndView = new ModelAndView("playlist/allplaylists");
    modelAndView.addObject("playlists", playlists);
    return modelAndView;
  }

  @GetMapping("/playlists")
  public ModelAndView myPlaylist(Principal principal){
    UserDTO user = userService.findByEmail(principal.getName());
    Set<Playlist> playlists = user.getCreatedPlaylists();
    ModelAndView modelAndView = new ModelAndView("playlist/playlists");
    modelAndView.addObject("playlists", playlists);
    return modelAndView;
  }

  @GetMapping("/playlists/{id}")
  public ModelAndView detailPlaylist(@PathVariable Long id) {
    Optional<Playlist> playlist = playlistService.findById(id);
    ModelAndView modelAndView;
    if (!playlist.isPresent()){
      modelAndView = new ModelAndView("404");
      return modelAndView;
    }
    modelAndView = new ModelAndView("playlist/detail");
    modelAndView.addObject("playlist",playlist.get());
    modelAndView.addObject("recommend_playlists", playlistService.findTop5ByOrderByCreatedAtDesc());
    return modelAndView;
  }

  @GetMapping("/playlists/update/{id}")
  public ModelAndView editPlaylist(@PathVariable Long id, Principal principal) {
    UserDTO user = userService.findByEmail(principal.getName());
    Optional<Playlist> playlist = playlistService.findById(id);
    ModelAndView modelAndView;
    if (playlist.isPresent()){
      if (playlist.get().getOwner() == user){
        List<SongDTO> allsongs = songService.findAll();
        List<SongDTO> checkedsongs = playlist.get().getSongs();
        List<SongDTO> unchecksongs = playlistService.uncheckedSongs(allsongs,checkedsongs);

        modelAndView = new ModelAndView("playlist/edit");
        modelAndView.addObject("playlist",playlist.get());
        modelAndView.addObject("allsongs", allsongs);
        modelAndView.addObject("checkedsongs", checkedsongs);
        modelAndView.addObject("unchecksongs", unchecksongs);
      } else {
        modelAndView = new ModelAndView("404");
      }
    } else {
      modelAndView = new ModelAndView("404");
    }

    return modelAndView;
  }

  @PostMapping("/playlists/update")
  public ModelAndView updatePlaylist(@ModelAttribute("playlist") Playlist playlist, RedirectAttributes redirectAttributes) {
    ModelAndView modelAndView = new ModelAndView("redirect:/playlists/update/"+ playlist.getId());
    convertIdToSong(playlist);
    redirectAttributes.addFlashAttribute("message", "Playlist was update!");
    return modelAndView;
  }

  @GetMapping("/playlists/delete/{id}")
  public ModelAndView deletePlaylist(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
    UserDTO user = userService.findByEmail(principal.getName());
    Optional<Playlist> playlist = playlistService.findById(id);
    ModelAndView modelAndView;
    if (playlist.get().getOwner() == user){
      modelAndView = new ModelAndView("redirect:/playlists");
      playlistService.remove(id);
      redirectAttributes.addFlashAttribute("message", "Playlist was deleted");
    } else {
      modelAndView = new ModelAndView("404");
    }
    return modelAndView;
  }

  @GetMapping("playlists/like/{id}")
  @ResponseBody
  public String likeSong (@PathVariable Long id, Principal principal) {
    Optional<Playlist> playlist = playlistService.findById(id);
    if (playlist.isPresent() && principal != null) {
      int like = playlistService.like(playlist.get(), userService.findByEmail(principal.getName()));
      return Integer.toString(like);
    } else if (principal == null) {
      return "no login";
    } else {
      return "not found";
    }
  }
}
