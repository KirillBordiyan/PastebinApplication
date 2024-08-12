package pet.project.pasteBinApplication.configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pet.project.pasteBinApplication.prop.MinioProperties;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
