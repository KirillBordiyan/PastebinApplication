package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNickName(String nickName);

    void deleteByNickName(String nickName);

    @Query(nativeQuery = true, value = "select file " +
            "from users_files file" +
            " where file.userFileId = :fileId and file.owner = " +
            "   (select * " +
            "   from users " +
            "   where u.nick_name = :nickName)")
    boolean isFileOwner(String nickName, Long fileId);

//    Optional<UserEntity> findByUserId(UUID id);
}
