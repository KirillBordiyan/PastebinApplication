package pet.project.pasteBinApplication.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.UserService;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role roleUser = roleRepository.findByRoleName("USER");
        Role roleAdmin = roleRepository.findByRoleName("ADMIN");
        Set<Role> roleSet = Set.of(roleUser, roleAdmin);

        UserEntity admin = new UserEntity();
        admin.setNickName("admin_1");
        admin.setEmail("email_admin_1@mail.com");
        admin.setPassword("admin_1_password");
        admin.setPasswordConfirm("admin_1_password");
        admin.setRoles(roleSet);

        userService.createUser(admin);
    }
}
