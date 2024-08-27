package pet.project.pasteBinApplication.web.dto.fileRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "File request body to get Put URL")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePutRequest {

    @Schema(description = "original file name")
    @NotNull(message = "FPRq: original file name cannot be null")
    @Length(max = 255, message = "file name must be smaller than 255 symb")
    String originalFileName;

    @Schema(description = "ownerNickName of file")
    @NotNull(message = "FPRq: ownerNickName (nick name) must not be null")
    @Length(max = 255, message = "owner nickname must not be smaller than 255 symb")
    String ownerNickName;
}
