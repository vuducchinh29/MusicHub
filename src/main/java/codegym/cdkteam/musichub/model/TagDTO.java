package codegym.cdkteam.musichub.model;

import codegym.cdkteam.musichub.model.song.SongDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TagDTO {
  @Id
  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<SongDTO> songs;

  public TagDTO(String name) {
    this.name = name;
  }
}
