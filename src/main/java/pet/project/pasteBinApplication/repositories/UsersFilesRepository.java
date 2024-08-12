package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.file.UserFile;

@Repository
public interface UsersFilesRepository extends JpaRepository<UserFile, Long> {
}