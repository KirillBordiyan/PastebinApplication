package pet.project.pasteBinApplication.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pet.project.pasteBinApplication.model.user.UserEntity;

@Repository("redisUserRepository")
public interface RedisUserRepository extends CrudRepository<UserEntity, String> {

}
