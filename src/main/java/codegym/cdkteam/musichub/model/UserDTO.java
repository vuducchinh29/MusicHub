package codegym.cdkteam.musichub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotEmpty(message = "Email is required")
  @Email(message = "Please input your email")
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @NotNull(message = "Name is required")
  @Column(name = "name", nullable = false)
  private String name;

  @Size(min = 6)
  @Column(name = "password", nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<RoleDTO> roles;

  @Column(name = "enabled")
  private boolean enabled;


}
