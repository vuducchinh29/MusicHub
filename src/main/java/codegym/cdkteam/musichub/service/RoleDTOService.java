package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.RoleDTO;

import java.util.List;

public interface RoleDTOService {
    List<RoleDTO> findAll();
    RoleDTO findByName(String name);
    void save(RoleDTO role);
    void delete(long id);
}
