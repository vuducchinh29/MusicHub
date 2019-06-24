package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.service.SongService;

import java.util.List;
import java.util.Optional;

public class SongServiceImpl implements SongService {
  @Override
  public List<Song> findAll() {
    return null;
  }

  @Override
  public Optional<Song> findById(long id) {
    return Optional.empty();
  }

  @Override
  public Song save(Song song) {
    return null;
  }

  @Override
  public void delete(long id) {

  }
}
