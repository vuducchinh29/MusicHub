package codegym.cdkteam.musichub.service.impl;

import codegym.cdkteam.musichub.model.Playlist;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.repository.PlaylistRepository;
import codegym.cdkteam.musichub.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired
  PlaylistRepository playlistRepository;

  @Override
  public List<Playlist> findAll() {
    return playlistRepository.findAll();
  }

  @Override
  public Optional<Playlist> findById(Long id) {
    return playlistRepository.findById(id);
  }

  @Override
  public void save(Playlist playlist) {
    playlistRepository.save(playlist);
  }

  @Override
  public void remove(Long id) {
    playlistRepository.deleteById(id);
  }

  @Override
  public List<SongDTO> uncheckedSongs(List<SongDTO> allsongs, List<SongDTO> checkedsongs){
    List<SongDTO> uncheckedSongs = new ArrayList<>();
    for (int i = 0; i < allsongs.size(); i++) {
      boolean check = false;
      for (int a = 0; a < checkedsongs.size(); a++) {
        if (allsongs.get(i).getId() == checkedsongs.get(a).getId()) {
          check = true;
        }
      }
      if (!check) {
        uncheckedSongs.add(allsongs.get(i));
      }
    }
    return uncheckedSongs;
  }
  @Override
  public int like (Playlist playlist, UserDTO user) {
    if (!playlist.getLikedUsers().contains(user)) {
      playlist.getLikedUsers().add(user);
    } else {
      playlist.getLikedUsers().remove(user);
    }
    return playlistRepository.save(playlist).getLikedUsers().size();
  }

  @Override
  public List<Playlist> findAllByOrderByCreatedAtDesc() {
    return playlistRepository.findAllByOrderByCreatedAtDesc();
  }

  @Override
  public List<Playlist> findTop5ByOrderByCreatedAtDesc() {
    return playlistRepository.findTop5ByOrderByCreatedAtDesc();
  }

  @Override
  public List<Playlist> findTop6ByOrderByListenDesc() {
    return playlistRepository.findTop6ByOrderByListenDesc();
  }

  List<Playlist> findAllByNameContaining(String name) {
    return playlistRepository.findAllByNameContaining(name);
  }

  @Override
  public void listen(Playlist playlist) {
    playlist.setListen(playlist.getListen() + 1);
    playlistRepository.save(playlist);
  }
}
