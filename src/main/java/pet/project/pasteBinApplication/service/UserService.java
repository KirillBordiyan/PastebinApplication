package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.user.UserEntity;

public interface UserService {

    UserEntity getById(Long userId);
    UserEntity getByLogin(String login);
    UserEntity updateUser(UserEntity userEntity);
    UserEntity createUser(UserEntity userEntity);
    void deleteUser(String login);
//    void deleteUser(Long id);

    boolean isUserFileOwner(String login, Long fileId);
}
