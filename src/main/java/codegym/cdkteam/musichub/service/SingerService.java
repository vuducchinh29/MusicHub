package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.Singer;

import java.util.List;
import java.util.Optional;

public interface SingerService {
    List<Singer> findAll();

    Optional<Singer> findById(Long id);

    void save(Singer singer);

    void remove(Long id);

    List<Singer> findAllByNameContaining(String name);

}
