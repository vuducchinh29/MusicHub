package codegym.cdkteam.musichub.model.song;

import codegym.cdkteam.musichub.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Name of song is required")
  private String name;

  @Column(name = "description")
  private String description;

  @NotEmpty(message = "Link of song is required")
  private String linkSong;

  @NotEmpty(message = "Link of image is required")
  private String linkImage;

  private String tags;

  private Date createdAt;

  private UserDTO owner;
}
