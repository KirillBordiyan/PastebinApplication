package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(nativeQuery = true,
            value = """
                    select u.nick_name, u.email, u.password, r.role_name
                    from users u
                    left join users_roles ur on u.nick_name = ur.nick_name
                    inner join role r on ur.role_id = r.role_id
                    where
                    u.nick_name = :nickName
                    """)
    Optional<UserEntity> findUserName(@Param("nickName") String nickName);

    @Query(nativeQuery = true,
            value = """
                    select u.nick_name, u.email, u.password, r.role_name
                    from users u
                    inner join users_roles ur on u.nick_name = ur.nick_name
                    inner join role r on ur.role_id = r.role_id
                    where u.email = :email
                    """)
    Optional<UserEntity> findByEmail(@Param("email") String email);

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

    void deleteByNickName(String nickName);
}
