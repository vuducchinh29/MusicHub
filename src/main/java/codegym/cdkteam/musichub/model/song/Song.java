package codegym.cdkteam.musichub.model.song;

import javax.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
}
