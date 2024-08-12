package pet.project.pasteBinApplication.prop;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String secret;
    private Long access;

    public JwtProperties(String secret, Long access) {
        this.secret = secret;
        this.access = TimeUnit.HOURS.toHours(access);
    }
}
