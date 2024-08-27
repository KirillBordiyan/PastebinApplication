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
@ConfigurationProperties(prefix = "spring.minio")
public class MinioProperties {

    private String bucket;
    private String url;
    private String accessKey;
    private String secretKey;

    public MinioProperties(String bucket, String url, String secretKey, String accessKey) {
        this.bucket = bucket;
        this.url = url;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
}
