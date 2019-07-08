package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
}
