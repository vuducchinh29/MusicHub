package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserDTOService {
    List<UserDTO> findAll();
    UserDTO findByEmail(String email);
    Optional<UserDTO> findById(long id);
    UserDTO save(UserDTO user);
    void delete(long id);
}
