package pet.project.pasteBinApplication.web.dto.fileRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "File request body to get Get URL")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileGetRequest {

    @Schema(description = "bucket file name")
    @NotNull(message = "FGRq: bucket file name cannot be null")
    @Length(max = 255, message = "bucket file name must be smaller than 255 symb")
    String bucketFileName;
}
