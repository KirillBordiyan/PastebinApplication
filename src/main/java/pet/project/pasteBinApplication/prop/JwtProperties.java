package pet.project.pasteBinApplication.prop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String secret;
    private Long access;
    private Long refresh;

    public JwtProperties(String secret, Long access, Long refresh) {
        this.secret = secret;
        this.access = access;
        this.refresh = refresh;
    }
}
