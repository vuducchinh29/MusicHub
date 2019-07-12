package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
  List<Playlist> findAll();

  Optional<Playlist> findById(Long id);

  void save(Playlist playlist);

  void remove(Long id);

  List<SongDTO> uncheckedSongs(List<SongDTO> allsongs, List<SongDTO> checkedsongs);

  int like (Playlist playlist, UserDTO user);

  List<Playlist> findAllByOrderByCreatedAtDesc();
}
