package codegym.cdkteam.musichub.model.song;

import codegym.cdkteam.musichub.model.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
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
}
