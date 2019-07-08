package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.song.SongDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<SongDTO, Long> {
    Page<SongDTO>findAllByNameContaining(String name, Pageable pageable);
}
