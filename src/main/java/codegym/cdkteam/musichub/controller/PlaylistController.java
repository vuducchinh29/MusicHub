package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
}
