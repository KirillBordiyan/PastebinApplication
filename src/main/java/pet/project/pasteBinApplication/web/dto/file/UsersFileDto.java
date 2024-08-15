package pet.project.pasteBinApplication.web.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;

import java.io.InputStream;
import java.util.Date;

@Data
@Schema(description = "Users file DTO")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersFileDto {

    //TODO нужен просто чтобы не путаться с pojo классом, если что - что-то уберется здесь, а не там

    @Schema(description = "File name")
    @NotNull(message = "DTO file: ID cannot be null!", groups = OnUpdateProcess.class)
    String fileName;


    @Schema(description = "File type", example = ".txt")
    String contentType;

    Long size;

    @Schema(description = "Expiration Date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    Date createdTime;

    transient InputStream stream;
}
