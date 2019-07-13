package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SingerRepository extends JpaRepository<Singer, Long> {
}
