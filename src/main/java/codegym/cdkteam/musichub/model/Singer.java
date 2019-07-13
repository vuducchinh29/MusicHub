package codegym.cdkteam.musichub.model;

import codegym.cdkteam.musichub.model.song.SongDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "singers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Singer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "linkImage")
    private String linkImage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "singer_song",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<SongDTO> songs;

}
