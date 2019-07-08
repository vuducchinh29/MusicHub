package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.TagDTO;
import codegym.cdkteam.musichub.repository.TagDTORepository;
import codegym.cdkteam.musichub.service.TagDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagDTOServiceImpl implements TagDTOService {
  @Autowired
  private TagDTORepository tagDTORepository;
  public List<TagDTO> findAll() {
    return tagDTORepository.findAll();
  }

  @Override
  public List<TagDTO> findAllByNameContaining(String name) {
    return tagDTORepository.findAllByNameContaining(name);
  }

  public TagDTO save(TagDTO tagDTO) {
    return tagDTORepository.save(tagDTO);
  }
}
