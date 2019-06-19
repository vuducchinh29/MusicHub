package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.passwordDTO.NewPasswordDTO;
import codegym.cdkteam.musichub.model.passwordDTO.PasswordDTO;
import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.service.TokenVerifyDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDTOService userDTOService;

  @Autowired
  private TokenVerifyDTOService tokenVerifyDTOService;


  @GetMapping("/user/update-password")
  public String updatePasswordForm() {
    return "updatePassword";
  }

  @PostMapping("/user/update-password")
  public String updatePassword(@ModelAttribute PasswordDTO passwordDTO) {
    UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    userDTO.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
    userDTOService.save(userDTO);
    TokenVerifyDTO tokenVerifyDTO = tokenVerifyDTOService.findByUserId(userDTO.getId());
    tokenVerifyDTOService.delete(tokenVerifyDTO);
    SecurityContextHolder.clearContext();

    return "redirect:/login";
  }

  @GetMapping("/user/change-password")
  public String changePasswordForm(Model model) {
    model.addAttribute("newPasswordDTO", new NewPasswordDTO());
    return "changePassword";
  }

  @PostMapping("/user/change-password")
  public String changePassword(@Valid @ModelAttribute NewPasswordDTO newPasswordDTO, BindingResult result,  Principal principal) {
    if (result.hasFieldErrors()) {
      return "changePassword";
    }
    String username = principal.getName();
    UserDTO userDTO = userDTOService.findByEmail(username);
    Boolean valid = passwordEncoder.matches(newPasswordDTO.getCurrentPassword(), userDTO.getPassword());
    if (!valid) {
      return "redirect:/user/change-password?invalid";
    }
    userDTO.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
    userDTOService.save(userDTO);
    return "redirect:/user/change-password?done";
  }
}
