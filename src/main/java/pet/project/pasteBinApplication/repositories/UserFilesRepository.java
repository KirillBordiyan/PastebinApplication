package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFilesRepository extends JpaRepository<UserFile, Long> {
//    Optional<UserFile> findById(Long id);
    Optional<UserFile> findByFileName(String fileName);

    @Query(nativeQuery = true,
    value = "select f " +
            "from users_files f" +
            "where f.nick_name = (select u from users u where u.nick_name = ?)")
    List<UserFile> findAllUsersFilesByNickName(String nickName);

    void deleteByFileName(String fileName);

}
