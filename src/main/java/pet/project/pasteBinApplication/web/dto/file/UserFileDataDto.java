package pet.project.pasteBinApplication.web.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnGetProcess;

@Data
@Schema(description = "Users file DTO")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFileDataDto {

    @Schema(description = "original file name")
    @NotNull(message = "UFD DTO: original file name cannot be null" ,groups = {OnGetProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "file name must be smaller than 255 symb",groups = {OnGetProcess.class, OnCreateProcess.class})
    String originalFileName;

    @Schema(description = "Unique file name")
    @NotNull(message = "UFD DTO: unique file name cannot be null", groups = {OnGetProcess.class})
    @Length(max = 510, message = "unique file name must be smaller than 510 symb", groups = {OnGetProcess.class})
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String bucketFileName;

    @Schema(description = "ownerNickName of file")
    @NotNull(message = "UFD DTO: ownerNickName (nick name) must not be null",groups = {OnGetProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "owner nickname must not be smaller than 255 symb", groups = {OnGetProcess.class, OnCreateProcess.class})
    @JsonIgnoreProperties(value = "email")
    UserDto ownerNickName;
}
