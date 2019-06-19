package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.service.TokenVerifyDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
      return "redirect:/";
    }
  }
}
