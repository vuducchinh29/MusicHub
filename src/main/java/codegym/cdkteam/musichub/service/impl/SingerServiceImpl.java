package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.SingerDTO;
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
    public List<SingerDTO> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public Optional<SingerDTO> findById(Long id) {
        return singerRepository.findById(id);
    }

    @Override
    public void save(SingerDTO singerDTO) {
        singerRepository.save(singerDTO);
    }

    @Override
    public void remove(Long id) {
        singerRepository.deleteById(id);
    }

    @Override
    public List<SingerDTO> findAllByNameContaining(String name) {
        return singerRepository.findAllByNameContaining(name);
    }

}
