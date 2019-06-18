package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.RoleDTO;
import codegym.cdkteam.musichub.model.TokenVerifyDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.service.RoleDTOService;
import codegym.cdkteam.musichub.service.TokenVerifyDTOService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.UUID;

@Controller
public class LoginController {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDTOService userDTOService;

  @Autowired
  private RoleDTOService roleDTOService;

  @Autowired
  private TokenVerifyDTOService tokenVerifyDTOService;

  @Autowired
  public JavaMailSender javaMailSender;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  public String user(Model model, Principal principal) {
    String username = principal.getName();
    UserDTO userDTO = userDTOService.findByEmail(username);
    if (!userDTO.isEnabled()) {
      return "verify";
    }
    org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    model.addAttribute("userInfo", loginedUser);
    return "user";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("userForm", new UserDTO());
    return "register";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute UserDTO userForm, Model model) {
//    if (result.hasFieldErrors()) {
//      return "register";
//    }

    UserDTO checkUser = userDTOService.findByEmail(userForm.getEmail());
    if (checkUser != null) {
      model.addAttribute("userForm", userForm);
      model.addAttribute("message", "Email exist");
      return "register";
    }

    UserDTO userDTO = new UserDTO();
    userDTO.setEmail(userForm.getEmail());
    userDTO.setName(userForm.getName());
    userDTO.setPassword(passwordEncoder.encode(userForm.getPassword()));
    HashSet<RoleDTO> roles = new HashSet<>();
    roles.add(roleDTOService.findByName("ROLE_MEMBER"));
    userDTO.setRoles(roles);
    userDTOService.save(userDTO);

    TokenVerifyDTO tokenVerifyDTO = new TokenVerifyDTO(UUID.randomUUID().toString(), userDTOService.findById(userDTO.getId()).get());
    tokenVerifyDTOService.save(tokenVerifyDTO);

    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(userDTO.getEmail());
    message.setSubject("Please Active Your Account");
    message.setText("Click link to active your account: http://localhost:8080/verify/" + userDTO.getId() + "?token=" + tokenVerifyDTO.getToken());
    this.javaMailSender.send(message);

    return "redirect:/login";
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  @GetMapping("/403")
  public String accessDenied() {
    return "403";
  }

  @GetMapping("/login")
  public String getLogin(Principal principal) {
    if (principal != null) {
      return "redirect:/";
    }
    return "login";
  }

  @GetMapping("/resetPassword")
  public String resetPasswordForm() {
    return "resetPassword";
  }

  @GetMapping("/resetPassword/{email}")
  public String resetPassword(@PathVariable String email) {
    UserDTO userDTO = userDTOService.findByEmail(email);
    if (userDTO == null) {
      return "redirect:/resetPassword";
    } else {
      TokenVerifyDTO token = new TokenVerifyDTO(UUID.randomUUID().toString(), userDTOService.findById(userDTO.getId()).get());
      tokenVerifyDTOService.save(token);
    }
    return "resetPassword1";
  }
}
