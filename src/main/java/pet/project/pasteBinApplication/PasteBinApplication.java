package pet.project.pasteBinApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PasteBinApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PasteBinApplication.class, args);

    }
    //minio in postman 9000 - as client/user
    //in browse 9001 - admin form

    //postgre sql
}
