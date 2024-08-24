package pet.project.pasteBinApplication.web.mappers;

import org.mapstruct.Mapper;
import pet.project.pasteBinApplication.model.user.UserEntity;
//import pet.project.pasteBinApplication.web.dto.file.UsersFileDto;
import pet.project.pasteBinApplication.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbleMapping<UserEntity, UserDto> {
    UserDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserDto dto);
}
