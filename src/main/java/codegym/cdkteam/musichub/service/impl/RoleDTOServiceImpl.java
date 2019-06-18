package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.RoleDTO;
import codegym.cdkteam.musichub.repository.RoleDTORepository;
import codegym.cdkteam.musichub.service.RoleDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDTOServiceImpl implements RoleDTOService {
  @Autowired
  private RoleDTORepository roleDTORepository;
  @Override
  public List<RoleDTO> findAll() {
    return roleDTORepository.findAll();
  }

  @Override
  public RoleDTO findByName(String name) {
    return roleDTORepository.findByName(name);
  }

  @Override
  public void save(RoleDTO roleDTO) {
    roleDTORepository.save(roleDTO);
  }

  @Override
  public void delete(long id) {
    roleDTORepository.deleteById(id);
  }
}
