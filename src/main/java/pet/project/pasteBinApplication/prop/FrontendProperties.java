package pet.project.pasteBinApplication.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "client")
public class FrontendProperties {
    String host;
    String port;
}
