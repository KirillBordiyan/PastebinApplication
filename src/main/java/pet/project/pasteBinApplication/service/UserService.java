package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.user.UserEntity;

public interface UserService {

    UserEntity getByNickName(String nickName);

    UserEntity getByEmail(String email);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity createUser(UserEntity userEntity);

    void deleteUserByNickName(String nickName);

    boolean isUserFileOwner(String nickName, String fileName);

}
