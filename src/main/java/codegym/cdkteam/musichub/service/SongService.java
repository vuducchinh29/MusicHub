package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SongService {
  Page<Song> findAll(Pageable pageable);
  Optional<Song> findById(long id);
  Song save(Song song);
  void delete(long id);
  Page<Song>findAllByNameContaining(String name, Pageable pageable);
}
