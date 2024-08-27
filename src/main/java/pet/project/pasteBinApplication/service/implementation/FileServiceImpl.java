package pet.project.pasteBinApplication.service.implementation;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.PresignedObjectUrlException;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.prop.MinioProperties;
import pet.project.pasteBinApplication.repositories.FileDataRepository;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;
import pet.project.pasteBinApplication.web.dto.fileRequest.FileGetRequest;
import pet.project.pasteBinApplication.web.dto.fileRequest.FilePutRequest;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;
import pet.project.pasteBinApplication.web.mappers.UserFileMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileDataRepository;
    private final UserService userService;
    private final MinioClient minioClient;
    private final UserFileMapper fileMapper;
    private final MinioProperties properties;

    @Override
    public List<UserFileData> getAllUserFiles(String nickName) {
        return List.copyOf((userService.getByNickName(nickName).getFiles()));
    }

    @Override
    public FilePutResponse generateBucketFileName(FilePutRequest filePutRequest) {

        String uniqueFileName = filePutRequest.getOwnerNickName() + "_" + filePutRequest.getOriginalFileName();
        FilePutResponse response = new FilePutResponse();

        if (fileDataRepository.findByBucketFileName(uniqueFileName).isPresent()) {
            response.setReplaced(true);
        }
        response.setUniqueFileName(uniqueFileName);
        return response;
    }

    @Override
    public FilePutResponse getPressignedPutUrl(FilePutRequest filePutRequest) {
        try {
            FilePutResponse response = generateBucketFileName(filePutRequest);

            if (!response.getReplaced()) {
                UserFileDataDto dto = new UserFileDataDto();
                dto.setOriginalFileName(filePutRequest.getOriginalFileName());
                dto.setOwnerNickName(filePutRequest.getOwnerNickName());
                dto.setBucketFileName(response.getUniqueFileName());

                fileDataRepository.save(fileMapper.toEntity(dto));
            }

            String presignedPutUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(properties.getBucket())
                            .object(response.getUniqueFileName())
                            .expiry(60 * 60 * 24)
                            .build());

            response.setPresignedPutUrl(presignedPutUrl);

            return response;
        } catch (Exception e) {
            throw new PresignedObjectUrlException("Pre-signed PUT link creating was failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileGetResponse getPressignedGetUrl(FileGetRequest fileGetRequest) {
        try {
            FileGetResponse response = new FileGetResponse();
            String presignedGetUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(properties.getBucket())
                            .object(fileGetRequest.getBucketFileName())
                            .expiry(24 * 60 * 60)
                            .build()
            );

            response.setUniqueFileName(fileGetRequest.getBucketFileName());
            response.setPresignedGetUrl(presignedGetUrl);
            return response;
        } catch (Exception e) {
            throw new PresignedObjectUrlException("Pre-signed GET link creating was failed: " + e.getMessage());
        }
    }
}
