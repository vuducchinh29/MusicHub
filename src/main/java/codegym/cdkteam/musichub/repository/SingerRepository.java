package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SingerRepository extends JpaRepository<Singer, Long> {
    List<Singer> findAllByNameContaining(String name);
}
