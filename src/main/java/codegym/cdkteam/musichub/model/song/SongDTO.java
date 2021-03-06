package codegym.cdkteam.musichub.model.song;

import codegym.cdkteam.musichub.model.SingerDTO;
import codegym.cdkteam.musichub.model.TagDTO;
import codegym.cdkteam.musichub.model.UserDTO;
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
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Lob
  private String description;

  @Column(name = "linkSong", nullable = false)
  private String linkSong;

  @Column(name = "linkImage")
  private String linkImage;

  @Column(name = "listen", columnDefinition = "int default 25")
  private Integer listen = 11;

  @CreationTimestamp
  @Column(name = "createdAt", updatable = false)
  private Date createdAt;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "song_tag",
          joinColumns = @JoinColumn(name = "song_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<TagDTO> tags;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "song_like",
          joinColumns = @JoinColumn(name = "song_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<UserDTO> likedUsers;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private UserDTO owner;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "song_singer",
      joinColumns = @JoinColumn(name = "song_id"),
      inverseJoinColumns = @JoinColumn(name = "singer_id")
  )
  private List<SingerDTO> singers;

  private String[] singerIDs;

  public List<String> getListEmailUserLiked() {
    List<String> listEmail = new ArrayList<>();
    for (UserDTO user: likedUsers) {
      listEmail.add(user.getEmail());
    }
    return listEmail;
  }
}
