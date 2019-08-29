package codegym.cdkteam.musichub;

import codegym.cdkteam.musichub.model.RoleDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.repository.RoleDTORepository;
import codegym.cdkteam.musichub.repository.UserDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserDTORepository userDTORepository;

    @Autowired
    private RoleDTORepository roleDTORepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Roles
        if (roleDTORepository.findByName("ROLE_ADMIN") == null) {
            roleDTORepository.save(new RoleDTO("ROLE_ADMIN"));
        }

        if (roleDTORepository.findByName("ROLE_MEMBER") == null) {
            roleDTORepository.save(new RoleDTO("ROLE_MEMBER"));
        }

        // Admin account
        if (userDTORepository.findByEmail("admin@gmail.com") == null) {
            UserDTO admin = new UserDTO();
            admin.setEmail("admin@gmail.com");
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleDTO> roles = new HashSet<>();
            roles.add(roleDTORepository.findByName("ROLE_ADMIN"));
            admin.setRoles(roles);
            admin.setEnabled(true);
            userDTORepository.save(admin);
        }

        // Member account
        if (userDTORepository.findByEmail("member@gmail.com") == null) {
            UserDTO user = new UserDTO();
            user.setEmail("member@gmail.com");
            user.setName("Member");
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleDTO> roles = new HashSet<>();
            roles.add(roleDTORepository.findByName("ROLE_MEMBER"));
            user.setRoles(roles);
            user.setEnabled(true);
            userDTORepository.save(user);
        }
    }
}
