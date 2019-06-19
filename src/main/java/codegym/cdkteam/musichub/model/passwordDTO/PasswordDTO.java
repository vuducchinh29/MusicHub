package codegym.cdkteam.musichub.model.passwordDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
  @Size(min = 6, message = "Password must be more than 6 character")
  private String password;
}
