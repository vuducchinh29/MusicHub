package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
  List<Playlist> findAllByOrderByCreatedAtDesc();
}
