package pet.project.pasteBinApplication.web.dto.fileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileGetResponse {

    String uniqueFileName;
    String presignedGetUrl;

}
