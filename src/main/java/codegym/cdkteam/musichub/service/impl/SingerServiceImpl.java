package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.Singer;
import codegym.cdkteam.musichub.repository.SingerRepository;
import codegym.cdkteam.musichub.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    SingerRepository singerRepository;
    @Override
    public List<Singer> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return singerRepository.findById(id);
    }

    @Override
    public void save(Singer singer) {
        singerRepository.save(singer);
    }

    @Override
    public void remove(Long id) {
        singerRepository.deleteById(id);
    }

    @Override
    public List<Singer> findAllByNameContaining(String name) {
        return singerRepository.findAllByNameContaining(name);
    }

}
