package pet.project.pasteBinApplication.web.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    UUID userId;
    String login;
    String access;
    String refresh;
}
