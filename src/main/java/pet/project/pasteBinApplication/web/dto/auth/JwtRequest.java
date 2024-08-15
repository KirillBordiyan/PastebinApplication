package pet.project.pasteBinApplication.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Schema(description = "Jwt Request class")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {
    @Schema(description = "User email", example = "email@mail.com")
    String email;
    @Schema(description = "User password encode", example = "123")
    String password;
}
