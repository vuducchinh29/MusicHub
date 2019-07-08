package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  @PostMapping("/create")
  public ModelAndView addNewPlaylist(@ModelAttribute("playlist") Playlist playlist, RedirectAttributes redirectAttributes) {
    ModelAndView modelAndView = new ModelAndView("redirect:/playlist/create");
    convertIdToSong(playlist);
    redirectAttributes.addFlashAttribute("message", "Create playlist success!");
    return modelAndView;
  }

  private void convertIdToSong(@ModelAttribute("playlist") Playlist playlist) {
    String[] listupdateSongID = playlist.getSongIDs();
    List<Song> updateListSong = new ArrayList<>();
    for (int i = 0; i < listupdateSongID.length; i++) {
      Long songID = Long.parseLong(listupdateSongID[i]);
      Song song = songService.findById(songID).get();
      updateListSong.add(song);
    }
    playlist.setSongs(updateListSong);
    playlistService.save(playlist);
  }

  @GetMapping("/list")
  public ModelAndView allPlaylist(){
    List<Playlist> playlists = playlistService.findAll();
    ModelAndView modelAndView = new ModelAndView("playlist/list");
    modelAndView.addObject("playlists", playlists);
    return modelAndView;
  }

  @GetMapping("/detail/{id}")
  public ModelAndView detailPlaylist(@PathVariable Long id) {
    Optional<Playlist> playlist = playlistService.findById(id);
    ModelAndView modelAndView;
    if (!playlist.isPresent()){
      modelAndView = new ModelAndView("404");
      return modelAndView;
    }
    List<Song> songs = playlist.get().getSongs();
    modelAndView = new ModelAndView("playlist/detail");
    modelAndView.addObject("playlist",playlist.get());
    modelAndView.addObject("songs", songs);
    return modelAndView;
  }

  @GetMapping("/edit/{id}")
  public ModelAndView editPlaylist(@PathVariable Long id) {
    Optional<Playlist> playlist = playlistService.findById(id);
    List<Song> allsongs = songService.findAll();
    List<Song> songs = playlist.get().getSongs();
    List<Song> uncheckSongs = playlistService.uncheckedSongs(allsongs,songs);

    ModelAndView modelAndView = new ModelAndView("playlist/edit");
    modelAndView.addObject("playlist",playlist.get());
    modelAndView.addObject("allsongs", allsongs);
    modelAndView.addObject("songs", songs);
    modelAndView.addObject("unchecksongs", uncheckSongs);
    return modelAndView;
  }

  @PostMapping("/edit")
  public ModelAndView updatePlaylist(@ModelAttribute("playlist") Playlist playlist, RedirectAttributes redirectAttributes) {
    ModelAndView modelAndView = new ModelAndView("redirect:/playlist/edit/"+ playlist.getId());
    convertIdToSong(playlist);
    redirectAttributes.addFlashAttribute("message", "Playlist was update!");
    return modelAndView;
  }

  @GetMapping("/delete/{id}")
  public ModelAndView deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    ModelAndView modelAndView = new ModelAndView("redirect:/playlist/list");
    playlistService.remove(id);
    redirectAttributes.addFlashAttribute("message", "Playlist was deleted");
    return modelAndView;
  }
}
