package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.PasswordDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    UserDTO savedUser = saveUserDTO(userForm);
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

  @GetMapping("/resetPassword")
  public String resetPasswordForm() {
    return "resetPassword";
  }

  @PostMapping("/resetPassword")
  public String resetPassword(@RequestParam String email) {
    UserDTO userDTO = userDTOService.findByEmail(email);
    if (userDTO == null) {
      return "redirect:/resetPassword?error";
    } else {
      TokenVerifyDTO tokenVerifyDTO = tokenVerifyDTOService.findByUserId(userDTO.getId());
      if (tokenVerifyDTO != null) {
        tokenVerifyDTOService.delete(tokenVerifyDTO);
      }
      sendMailResetPassword(userDTO);
    }
    return "redirect:/resetPassword";
  }

  @GetMapping("/user/updatePassword")
  public String updatePasswordForm() {
    return "updatePassword";
  }

  @PostMapping("/user/updatePassword")
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
    message.setText("Click link to reset password: http://localhost:8080/user/resetPassword?id=" + user.getId() + "&token=" + token.getToken());
    this.javaMailSender.send(message);
  }
}
