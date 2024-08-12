package pet.project.pasteBinApplication.web.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;

import java.io.InputStream;
import java.util.Date;

@Data
public class UsersFileDto {
    //FIXME с этим DTO подумать, далеко не все нужное

    //TODO нужен просто чтобы не путаться с pojo классом, если что - что-то уберется здесь, а не там
    @NotNull(message = "DTO file: ID cannot be null!", groups = OnUpdateProcess.class)
    String fileName;
    String contentType;
    Long size;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    Date createdTime;
    transient InputStream stream;
}
