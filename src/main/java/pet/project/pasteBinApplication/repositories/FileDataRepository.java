package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.file.UserFileData;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<UserFileData, Long> {

    Optional<UserFileData> findByLinkedFileName(String linkedFileName);

//    есть у пользователя
//    List<UserFileData> findAllUserFiles(String nickName);

}
