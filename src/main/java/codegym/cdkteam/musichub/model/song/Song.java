package codegym.cdkteam.musichub.model.song;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "songs")
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotEmpty(message = "Name of song is required")
  @Column(name = "name", nullable = false)
  private String name;
}
