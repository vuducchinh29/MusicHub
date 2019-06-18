package codegym.cdkteam.musichub.repository;

import codegym.cdkteam.musichub.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDTORepository extends JpaRepository<UserDTO, Long> {
    UserDTO findByEmail(String email);

}
