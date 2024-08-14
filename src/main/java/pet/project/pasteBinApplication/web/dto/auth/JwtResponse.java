package pet.project.pasteBinApplication.web.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    Long userId;
    String login;
    String access;
    String refresh;
}
