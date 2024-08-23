package pet.project.pasteBinApplication.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.model.user.Role;

@RequiredArgsConstructor
@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role user = new Role();
        user.setRoleName("USER");
        roleRepository.save(user);

        Role admin = new Role();
        admin.setRoleName("ADMIN");
        roleRepository.save(admin);

    }
}
