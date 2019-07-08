package codegym.cdkteam.musichub.service;

import codegym.cdkteam.musichub.model.TagDTO;

import java.util.List;

public interface TagDTOService {
  List<TagDTO> findAll();
  List<TagDTO> findAllByNameContaining(String name);
  TagDTO save(TagDTO tagDTO);
}
