package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDTORepository extends JpaRepository<RoleDTO, Long> {
    RoleDTO findByName(String name);
}
