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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    User loginedUser = (User) ((Authentication) principal).getPrincipal();
    model.addAttribute("userInfo", loginedUser);
    return "user";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("userDTO", new UserDTO());
    return "register";
  }

  @PostMapping("/register")
  public String register(@Valid @ModelAttribute UserDTO userDTO, BindingResult result, Model model) {
    if (result.hasFieldErrors()) {
      return "register";
    }

    UserDTO checkUser = userDTOService.findByEmail(userDTO.getEmail());
    if (checkUser != null) {
      model.addAttribute("userDTO", userDTO);
      model.addAttribute("message", "Email exist");
      return "register";
    }

    UserDTO savedUser = saveUserDTO(userDTO);
    sendMailActive(savedUser);
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

  @GetMapping("/reset-password")
  public String resetPasswordForm() {
    return "resetPassword";
  }

  @PostMapping("/reset-password")
  public String resetPassword(@RequestParam String email) {
    UserDTO userDTO = userDTOService.findByEmail(email);
    if (userDTO == null) {
      return "redirect:/reset-password?error";
    } else {
      TokenVerifyDTO tokenVerifyDTO = tokenVerifyDTOService.findByUserId(userDTO.getId());
      if (tokenVerifyDTO != null) {
        tokenVerifyDTOService.delete(tokenVerifyDTO);
      }
      sendMailResetPassword(userDTO);
    }
    return "redirect:/reset-password";
  }

  private UserDTO saveUserDTO(UserDTO userForm) {
    UserDTO userDTO = new UserDTO();
    userDTO.setEmail(userForm.getEmail());
    userDTO.setName(userForm.getName());
    userDTO.setPassword(passwordEncoder.encode(userForm.getPassword()));
    HashSet<RoleDTO> roles = new HashSet<>();
    roles.add(roleDTOService.findByName("ROLE_MEMBER"));
    userDTO.setRoles(roles);
    return userDTOService.save(userDTO);
  }

  private void sendMailActive(UserDTO user) {
    TokenVerifyDTO tokenVerifyDTO = new TokenVerifyDTO(UUID.randomUUID().toString(), userDTOService.findById(user.getId()).get());
    tokenVerifyDTOService.save(tokenVerifyDTO);

    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(user.getEmail());
    message.setSubject("Please Active Your Account");
    message.setText("Click link to active your account: http://localhost:8080/user/active?id=" + user.getId() + "&token=" + tokenVerifyDTO.getToken());
    this.javaMailSender.send(message);
  }

  private void sendMailResetPassword(UserDTO user) {
    TokenVerifyDTO token = new TokenVerifyDTO(UUID.randomUUID().toString(), userDTOService.findById(user.getId()).get());
    tokenVerifyDTOService.save(token);

    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(user.getEmail());
    message.setSubject("Reset Password");
    message.setText("Click link to reset password: http://localhost:8080/user/reset-password?id=" + user.getId() + "&token=" + token.getToken());
    this.javaMailSender.send(message);
  }
}
