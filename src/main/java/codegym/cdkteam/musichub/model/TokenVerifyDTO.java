package codegym.cdkteam.musichub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token_verify")
public class TokenVerifyDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "token")
  private String token;

  @OneToOne
  private UserDTO user;

  public TokenVerifyDTO(String token, UserDTO user) {
    this.token = token;
    this.user = user;
  }
}
