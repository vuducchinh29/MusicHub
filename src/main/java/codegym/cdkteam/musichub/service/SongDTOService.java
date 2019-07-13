package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SongDTOService {
  List<SongDTO> findAll();
  Page<SongDTO> findAll(Pageable pageable);
  Optional<SongDTO> findById(long id);
  Song findByIdWithTagIsString(long id);
  SongDTO save(Song song);
  void delete(long id);

  List<SongDTO> findAllByNameContaining(String name);
  List<SongDTO> findAllByOrderByCreatedAtDesc();

  int like (SongDTO song, UserDTO user);

  void listen(SongDTO song);
}
