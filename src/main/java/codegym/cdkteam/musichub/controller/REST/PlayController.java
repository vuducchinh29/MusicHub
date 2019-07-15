package codegym.cdkteam.musichub.controller.REST;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.PlaylistService;
import codegym.cdkteam.musichub.service.SongDTOService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PlayController {
  @Autowired
  private SongDTOService songDTOService;

  @Autowired
  private PlaylistService playlistService;
  @GetMapping("/songs/play/{songId}")
  public ResponseEntity playSong(@PathVariable Long songId) {
    Optional<SongDTO> song = songDTOService.findById(songId);
    if (song.isPresent()) {
      songDTOService.listen(song.get());
      SongPlay songPlay = convertSongDTOToSongPlay(song.get());
      return ResponseEntity.status(HttpStatus.OK).body(songPlay);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
  }

  @GetMapping("/playlists/play/{playlistId}")
  public ResponseEntity playPlaylist (@PathVariable Long playlistId) {
    Optional<Playlist> playlist = playlistService.findById(playlistId);
    if (playlist.isPresent()) {
      playlistService.listen(playlist.get());
      List<SongPlay> songs = new ArrayList();
      for (SongDTO song: playlist.get().getSongs()) {
        songs.add(convertSongDTOToSongPlay(song));
      }
      return ResponseEntity.status(HttpStatus.OK).body(songs);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
  }
  private SongPlay convertSongDTOToSongPlay (SongDTO songDTO) {
    SongPlay songPlay = new SongPlay();
    songPlay.setIcon(songDTO.getLinkImage());
    songPlay.setTitle(songDTO.getName());
    songPlay.setFile(songDTO.getLinkSong());
    return songPlay;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  private class SongPlay{
    private String icon;
    private String title;
    private String file;
  }
}
