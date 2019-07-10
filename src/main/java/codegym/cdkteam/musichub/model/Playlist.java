package codegym.cdkteam.musichub.model;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "playlist")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "song_playlist",
      joinColumns = @JoinColumn(name = "playlist_id"),
      inverseJoinColumns = @JoinColumn(name = "song_id"))
  private List<SongDTO> songs;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "playlist_like",
          joinColumns = @JoinColumn(name = "playlist_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserDTO> likedUsers;

  private String[] songIDs;
}
