package pet.project.pasteBinApplication.web.mappers;

import org.mapstruct.Mapper;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.web.dto.file.UsersFileDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFileMapper extends AbleMapping<UserFile, UsersFileDto>{
//    UsersFileDto toDto(UserFile file);
//    List<UsersFileDto> toListDto(List<UserFile> files);
//    UserFile toEntity(UsersFileDto dto);
}
