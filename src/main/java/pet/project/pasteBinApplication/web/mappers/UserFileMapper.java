package pet.project.pasteBinApplication.web.mappers;

import org.mapstruct.Mapper;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFileMapper extends AbleMapping<UserFileData, UserFileDataDto>{
    UserFileDataDto toDto(UserFileData file);
    List<UserFileDataDto> toDto(List<UserFileData> files);
    UserFileData toEntity(UserFileDataDto dto);
}
