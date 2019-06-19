package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.service.TokenVerifyDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class VerifyController {
  @Autowired
  private TokenVerifyDTOService tokenVerifyDTOService;
  @Autowired
  private UserDTOService userDTOService;

  @GetMapping("/user/active")
  public String active(@RequestParam("id") long userId, @RequestParam Optional<String> token) {
    TokenVerifyDTO tokenVerifyDTO = tokenVerifyDTOService.findByUserId(userId);
    if (tokenVerifyDTO == null) {
      return "redirect:/404";
    } else {
      String realToken = tokenVerifyDTO.getToken();
      if (token.get().equals(realToken)) {
        UserDTO user = userDTOService.findById(userId).get();
        user.setEnabled(true);
        userDTOService.save(user);
        tokenVerifyDTOService.delete(tokenVerifyDTO);
      } else {
        return "redirect:/404";
      }
      return "redirect:/login";
    }
  }

  @GetMapping("/user/reset-password")
  public String resetPassword(@RequestParam(name = "id") Optional<Long> userId, @RequestParam Optional<String> token) {
    if (!(userId.isPresent() || token.isPresent())) return "redirect:/404";

    String result = validatePasswordResetToken(userId.get(), token.get());
    if (result != null) {
      return "redirect:/404";
    }
    return "redirect:/user/update-password";
  }

  public String validatePasswordResetToken(long userId, String token) {
    TokenVerifyDTO passToken = tokenVerifyDTOService.findByUserId(userId);
    if (passToken == null) {
      return "invalidToken";
    }
    Boolean result = token.equals(passToken.getToken());
    if (!result) {
      return "invalidToken";
    }
    UserDTO user = userDTOService.findById(userId).get();
    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(
            new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
    SecurityContextHolder.getContext().setAuthentication(auth);
    return null;
  }
}
