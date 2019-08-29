package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDTORepository extends JpaRepository<TagDTO, String> {
  List<TagDTO> findAllByNameContaining(String name);
}