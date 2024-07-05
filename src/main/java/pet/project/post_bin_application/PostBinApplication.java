package pet.project.post_bin_application;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class PostBinApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostBinApplication.class, args);


    }
    //minio in postman 9000 - as client/user
    //in browse 9001 - admin form

    //postgre sql
}
