package pet.project.pasteBinApplication.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Schema(description = "Jwt Response class")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    @Schema(description = "Access Token")
    String access;

    @Schema(description = "Refresh Token")
    String refresh;
}
