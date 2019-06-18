package codegym.cdkteam.musichub.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "persistent_logins")
public class PersistentLoginsDTO {
  @NotNull
  private String username;

  @Id
  @NotNull
  private String series;

  @NotNull
  private String token;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date last_used;
}
