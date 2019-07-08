package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.song.Song;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
  List<Playlist> findAll();

  Optional<Playlist> findById(Long id);

  void save(Playlist playlist);

  void remove(Long id);

  List<Song> uncheckedSongs(List<Song> allsongs, List<Song> songs);
}
