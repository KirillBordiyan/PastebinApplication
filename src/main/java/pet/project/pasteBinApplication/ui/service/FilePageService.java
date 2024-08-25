package pet.project.pasteBinApplication.ui.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pet.project.pasteBinApplication.exceptions.FileUploadException;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.prop.MinioProperties;
import pet.project.pasteBinApplication.repositories.FileDataRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FilePageService {

    private final MinioClient minio;
    private final MinioProperties minioProperties;

    public File createFile(String nickName, String originalFileName, String content) {
        try {
            File resultFile = new File(originalFileName + ".txt");
            FileWriter writer = new FileWriter(resultFile);
            writer.write(content);
            writer.close();

            return resultFile;
        } catch (Exception e) {
            throw new FileUploadException("File upload exc: " + e.getMessage());
        }
    }


    public /*void*/ String upload(File toUploadFile) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadException("1) File upload failed: " + e.getMessage());
        }

        try {
            saveFile(new FileInputStream(toUploadFile.getName()), toUploadFile.getName());
        } catch (Exception e){
            throw new FileUploadException("2) File upload failed: " + e.getMessage());
        }

        return toUploadFile.getName();
    }

    @SneakyThrows
    private void createBucket() {
        boolean exists = minio.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!exists) {
            minio.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String fileName) {
        minio.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

}
