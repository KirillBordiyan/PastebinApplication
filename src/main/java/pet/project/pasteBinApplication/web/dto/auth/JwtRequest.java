package pet.project.pasteBinApplication.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Schema(description = "Jwt Request class")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {

    @Schema(description = "User nick name EMAIL NOW", example = "email@mail.com")
    String email;

    @Schema(description = "User password encode", example = "123")
    String password;
}
