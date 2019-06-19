package codegym.cdkteam.musichub.model.passwordDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDTO {
  @Size(min = 6, message = "Password > 6")
  private String currentPassword;

  @Size(min = 6, message = "Password > 6")
  private String newPassword;
}
