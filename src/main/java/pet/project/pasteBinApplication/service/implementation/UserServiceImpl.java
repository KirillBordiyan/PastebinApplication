package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    46 минут

    @Override
    public UserEntity getById(UUID userId) {
        return null;
    }

    @Override
    public UserEntity getByLogin(String login) {
        return null;
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public void deleteUser(String login) {

    }

    @Override
    public void deleteUser(UUID id) {

    }
}
