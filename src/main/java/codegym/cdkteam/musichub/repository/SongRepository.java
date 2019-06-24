package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
  Song findById(long id);
}
