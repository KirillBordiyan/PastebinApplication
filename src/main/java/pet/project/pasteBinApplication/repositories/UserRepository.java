package pet.project.pasteBinApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByLogin(String login);

//    User updateUser(User user);
//    User createUser(User user);
//    boolean isFileOwner(String login, String fileName);


}
