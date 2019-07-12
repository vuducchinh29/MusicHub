package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.TagDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.repository.SongRepository;
import codegym.cdkteam.musichub.repository.TagDTORepository;
import codegym.cdkteam.musichub.service.SongDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class SongDTOServiceImpl implements SongDTOService {
  @Autowired
  SongRepository songRepository;
  @Autowired
  TagDTORepository tagDTORepository;

  @Override
  public List<SongDTO> findAll() {
    return songRepository.findAll();
  }

  @Override
  public Page<SongDTO> findAll(Pageable pageable) {
    return songRepository.findAll(pageable);
  }

  @Override
  public Optional<SongDTO> findById(long id) {
    return songRepository.findById(id);
  }

  @Override
  public Song findByIdWithTagIsString(long id) {
    SongDTO songDTO = songRepository.findById(id).get();
    Song song = new Song();
    song.setId(songDTO.getId());
    song.setName(songDTO.getName());
    song.setDescription(songDTO.getDescription());
    song.setLinkSong(songDTO.getLinkSong());
    song.setLinkImage(songDTO.getLinkImage());
    song.setOwner(songDTO.getOwner());
    String tags = "";
    for (TagDTO tag: songDTO.getTags()) {
      tags += tag.getName() + ',';
    }
    tags = tags.substring(0, tags.length() - 1);
    song.setTags(tags);
    return song;
  }

  @Override
  public SongDTO save(Song song) {
    SongDTO songDTO = new SongDTO();
    songDTO.setId(song.getId());
    songDTO.setDescription(song.getDescription());
    songDTO.setName(song.getName());
    songDTO.setLinkSong(song.getLinkSong());
    songDTO.setLinkImage(song.getLinkImage());
    songDTO.setOwner(song.getOwner());

    HashSet<TagDTO> tags = new HashSet<>();
    String[] tagList = song.getTags().split(",");
    for (String tag: tagList) {
      tag = tag.trim();
      Optional<TagDTO> tagDTO = tagDTORepository.findById(tag);
      if (tagDTO.isPresent()) {
        tags.add(tagDTO.get());
      } else {
        TagDTO savedTag = tagDTORepository.save(new TagDTO(tag));
        tags.add(savedTag);
      }
    }
    songDTO.setTags(tags);
    return songRepository.save(songDTO);
  }

  @Override
  public void delete(long id) {
    songRepository.deleteById(id);
  }

  @Override
  public Page<SongDTO> findAllByNameContaining(String name, Pageable pageable) {
    return songRepository.findAllByNameContaining(name, pageable);
  }

  @Override
  public Page<SongDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
    return songRepository.findAllByOrderByCreatedAtDesc(pageable);
  }

  @Override
  public int like(SongDTO song, UserDTO user) {
    if (!song.getLikedUsers().contains(user)) {
      song.getLikedUsers().add(user);
    } else {
      song.getLikedUsers().remove(user);
    }
    SongDTO savedSong = songRepository.save(song);
    return savedSong.getLikedUsers().size();
  }
}
