package pet.project.pasteBinApplication.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.file.UserFile;

@Repository("redisUserFileRepository")
public interface RedisUserFileRepository extends CrudRepository<UserFile, String> {

}
