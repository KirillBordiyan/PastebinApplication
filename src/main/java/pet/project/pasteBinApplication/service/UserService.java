package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.UUID;

public interface UserService {

    UserEntity getById(UUID userId);
    UserEntity getByLogin(String login);
    UserEntity updateUser(UserEntity userEntity);
    UserEntity createUser(UserEntity userEntity);
    void deleteUser(String login);
    void deleteUser(UUID id);
}
