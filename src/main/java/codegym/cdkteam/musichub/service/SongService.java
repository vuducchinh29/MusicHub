package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SongService {
  Page<Song> findAll(Pageable pageable);

  List<Song> findAll();

  Optional<Song> findById(long id);
  Song save(Song song);
  void delete(long id);
  Page<Song>findAllByNameContaining(String name, Pageable pageable);
}
