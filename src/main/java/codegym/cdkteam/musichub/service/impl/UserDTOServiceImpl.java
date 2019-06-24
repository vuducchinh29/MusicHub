package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.repository.UserDTORepository;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDTOServiceImpl implements UserDTOService {
  @Autowired
  private UserDTORepository userDTORepository;
  @Override
  public List<UserDTO> findAll() {
    return userDTORepository.findAll();
  }

  @Override
  public UserDTO findByEmail(String email) {
    return userDTORepository.findByEmail(email);
  }

  @Override
  public Optional<UserDTO> findById(long id) {
    return userDTORepository.findById(id);
  }

  @Override
  public UserDTO save(UserDTO userDTO) {
    return userDTORepository.save(userDTO);
  }

  @Override
  public void delete(long id) {
    userDTORepository.deleteById(id);
  }
}
