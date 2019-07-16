package codegym.cdkteam.musichub.model;

import codegym.cdkteam.musichub.model.song.Song;
import codegym.cdkteam.musichub.model.song.SongDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

  @Column(name = "linkAvatar")
  private String linkAvatar;

  @Column(name = "listen")
  private Integer listen;

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

  @ManyToOne
  @JoinColumn(name = "user_id", updatable = false)
  private UserDTO owner;

  private String[] songIDs;

  @CreationTimestamp
  @Column(name = "createdAt", updatable = false)
  private Date createdAt;

  public List<String> getListEmailUserLiked() {
    List<String> listEmail = new ArrayList<>();
    for (UserDTO user: likedUsers) {
      listEmail.add(user.getEmail());
    }
    return listEmail;
  }
}
