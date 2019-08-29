package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.SingerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SingerRepository extends JpaRepository<SingerDTO, Long> {
    List<SingerDTO> findAllByNameContaining(String name);
}
