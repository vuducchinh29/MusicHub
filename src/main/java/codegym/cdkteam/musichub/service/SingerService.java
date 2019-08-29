package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.SingerDTO;

import java.util.List;
import java.util.Optional;

public interface SingerService {
    List<SingerDTO> findAll();

    Optional<SingerDTO> findById(Long id);

    void save(SingerDTO singerDTO);

    void remove(Long id);

    List<SingerDTO> findAllByNameContaining(String name);

}
