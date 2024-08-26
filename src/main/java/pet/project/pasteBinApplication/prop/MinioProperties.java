package pet.project.pasteBinApplication.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String bucket = "bucket1";
    private String url = "http://localhost:9000";
    private String accessKey;
    private String secretKey;

}
