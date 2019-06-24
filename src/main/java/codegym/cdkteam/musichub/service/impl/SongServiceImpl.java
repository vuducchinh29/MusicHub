package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.repository.SongRepository;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SongServiceImpl implements SongService {
  @Autowired
  SongRepository songRepository;

  @Override
  public List<Song> findAll() {
    return songRepository.findAll();
  }

  @Override
  public Optional<Song> findById(long id) {
    return songRepository.findById(id);
  }

  @Override
  public Song save(Song song) {
    return songRepository.save(song);
  }

  @Override
  public void delete(long id) {
    songRepository.deleteById(id);
  }
}
