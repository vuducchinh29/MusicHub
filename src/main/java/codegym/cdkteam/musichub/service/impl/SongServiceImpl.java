package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.repository.SongRepository;
import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SongServiceImpl implements SongService {
  @Autowired
  SongRepository songRepository;


  @Override
  public Page<Song> findAll(Pageable pageable) {
    return songRepository.findAll(pageable);
  }

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

  @Override
  public Page<Song> findAllByNameContaining(String name, Pageable pageable) {
    return songRepository.findAllByNameContaining(name, pageable);
  }
}
