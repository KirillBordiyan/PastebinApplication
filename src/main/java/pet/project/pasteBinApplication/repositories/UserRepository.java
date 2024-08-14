package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);
    void deleteByLogin(String login);

    @Query(nativeQuery = true, value = "select file " +
            "from users_files file" +
            " where file.userFileId = :fileId and file.owner = " +
            "   (select * " +
            "   from users " +
            "   where u.login = :login)")
    boolean isFileOwner(String login, Long fileId);


    //    Optional<UserEntity> findById(UUID id);
    //    void updateUser(UserEntity user);
    //    void createUser(UserEntity user);
    //    void insertUserRole(Long userId, Role role); //или чделать логином?
}
