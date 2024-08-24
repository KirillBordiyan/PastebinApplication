package pet.project.pasteBinApplication.web.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import pet.project.pasteBinApplication.web.dto.user.UserDto;

@Data
@Schema(description = "Users file DTO")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFileDataDto {

    @Schema(description = "original file name")
    @NotNull(message = "UFD DTO: original file name cannot be null"/*, groups = OnCreateProcess.class*/)
    @Length(max = 255, message = "file name must be smaller than 255 symb"/*, groups = OnCreateProcess.class*/)
    String originalFileName;

    @Schema(description = "ownerNickName of file")
    @NotNull(message = "UFD DTO: ownerNickName (nick name) must not be null"/*, groups = OnCreateProcess.class*/)
    UserDto ownerNickName;
}
