package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByNickName(String nickName);

    Optional<UserEntity> findByEmail(String email);

    void deleteByNickName(String nickName);

    @Query(nativeQuery = true,
            value = """
                    select *
                    from users_files file
                    join users on file.nick_name = users.nick_name
                    where
                    file.original_file_name = :fileName and
                    users.nick_name = :nickName
                    """
    )
    boolean isFileOwner(@Param("nickName") String nickName, @Param("fileName") String fileName);
}
