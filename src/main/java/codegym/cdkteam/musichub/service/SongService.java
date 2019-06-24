package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.song.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
  List<Song> findAll();
  Optional<Song> findById(long id);
  Song save(Song song);
  void delete(long id);
}
