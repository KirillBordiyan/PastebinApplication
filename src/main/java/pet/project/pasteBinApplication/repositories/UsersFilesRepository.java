package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersFilesRepository extends JpaRepository<UserFile, Long> {
//    Optional<UserFile> findById(Long id);
    Optional<UserFile> findByFileName(String fileName);

    @Query(nativeQuery = true,
    value = "select f " +
            "from users_files f" +
            "where f.user_id = (select u from users u where u.login = ?)")
    List<UserFile> findAllUsersFilesByLogin(String login);

//    @Query(nativeQuery = true,
//    value = "insert into users_files (fileId, userId) values (?, ?)")
//    void assignToUserById(Long fileId, Long userId);

//    void update(UserFile userFile);
//    void create(UserFile userFile);
//    void delete(Long id);


}
