package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.repositories.RoleRepository;
import pet.project.pasteBinApplication.repositories.UserRepository;
import pet.project.pasteBinApplication.service.UserService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity userEntity) {

        if(Objects.isNull(userEntity.getUserName())){
            throw new IllegalStateException("UPDATE: User name must not be null!");
        }
        if(Objects.isNull(userEntity.getLogin())){
            throw new IllegalStateException("UPDATE: Login must not be null!");
        }
        if(Objects.isNull(userEntity.getPassword())){
            throw new IllegalStateException("UPDATE: Password must not be null!");
        }
        if(Objects.isNull(userEntity.getPasswordConfirm())){
            throw new IllegalStateException("UPDATE: Password confirm must not be null!");
        }
        if(Objects.isNull(userEntity.getRoles())){
            throw new IllegalStateException("UPDATE: Roles must not be null!");
        }

        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        if(Objects.isNull(userEntity.getUserName())){
            throw new IllegalStateException("CREATE: User name must not be null!");
        }
        if(Objects.isNull(userEntity.getLogin())){
            throw new IllegalStateException("CREATE: Login must not be null!");
        }
        if(Objects.isNull(userEntity.getPassword())){
            throw new IllegalStateException("CREATE: Password must not be null!");
        }
        if(Objects.isNull(userEntity.getPasswordConfirm())){
            throw new IllegalStateException("CREATE: Password confirm must not be null!");
        }
//        if(Objects.isNull(userEntity.getRoles())){
//            throw new IllegalStateException("CREATE: Roles must not be null!");
//        }
        if(userRepository.findByLogin(userEntity.getLogin()).isPresent()){
            throw new IllegalStateException("CREATE: User already exists!");
        }
        if(!userEntity.getPassword().equals(userEntity.getPasswordConfirm())){
            throw new IllegalStateException("CREATE: Password and password confirmation are different!");
        }

        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.setPasswordConfirm(encoder.encode(userEntity.getPasswordConfirm()));
        Role role = roleRepository.findByRoleName("USER");
        Set<Role> roles = Set.of(role);
        Set<UserFile> files = new HashSet<>();

        userEntity.setRoles(roles);
        userEntity.setUsersFiles(files);
        userRepository.save(userEntity);

        return userEntity;
    }

    @Override
    @Transactional
    public void deleteUser(String login) {
        userRepository.deleteByLogin(login);
    }

//    @Override
//    @Transactional
//    public void deleteUser(UUID id) {
//        userRepository.deleteById(id);
//    }

    @Override
    @Transactional
    public boolean isUserFileOwner(String login, Long fileId) {
        return userRepository.isFileOwner(login, fileId);
    }
}
