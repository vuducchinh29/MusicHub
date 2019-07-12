package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.song.SongDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<SongDTO, Long> {
  List<SongDTO> findAllByNameContaining(String name);

  List<SongDTO> findAllByOrderByCreatedAtDesc();
}
