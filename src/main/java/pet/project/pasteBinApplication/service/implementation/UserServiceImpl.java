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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional(readOnly = true)
    public UserEntity getByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found (by name)"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found (by login/email)"));
    }


    @Override
    @Transactional
    public UserEntity updateUser(UserEntity userEntity) {

        if(Objects.isNull(userEntity.getNickName())){
            throw new IllegalStateException("UPDATE: User name must not be null!");
        }
        if(Objects.isNull(userEntity.getEmail())){
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
        if(Objects.isNull(userEntity.getNickName())){
            throw new IllegalStateException("CREATE: User display name must not be null!");
        }
        if(Objects.isNull(userEntity.getEmail())){
            throw new IllegalStateException("CREATE: Email must not be null!");
        }
        if(Objects.isNull(userEntity.getPassword())){
            throw new IllegalStateException("CREATE: Password must not be null!");
        }
        if(Objects.isNull(userEntity.getPasswordConfirm())){
            throw new IllegalStateException("CREATE: Password confirm must not be null!");
        }
        if(userRepository.findByNickName(userEntity.getNickName()).isPresent()){
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
    public void deleteUserByNickName(String nickName) {
        userRepository.deleteByNickName(nickName);
    }


    @Override
    @Transactional
    public boolean isUserFileOwner(String nickName, Long fileId) {
        return userRepository.isFileOwner(nickName, fileId);
    }
}
