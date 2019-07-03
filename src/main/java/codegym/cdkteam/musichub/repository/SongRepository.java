package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song>findAllByNameContaining(String name, Pageable pageable);
}
