package pet.project.pasteBinApplication.service.implementation;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pet.project.pasteBinApplication.exceptions.FileUploadException;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.prop.MinioProperties;
//import pet.project.pasteBinApplication.repositories.UserFilesRepository;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.service.UsersFilesService;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UsersFilesService {

    private final MinioClient minio;
    private final MinioProperties minioProperties;

    @Override
    public String upload(UserFile toUploadFile) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }

        MultipartFile file = toUploadFile.getFile();
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new FileUploadException("File upload failed: file is empty or must have name");
        }

        String fileName = generateFileName(file);
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }

        saveFile(inputStream, fileName);
        return fileName;
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

    private String generateFileName(MultipartFile file) {
        String fileType = getType(file);
        return UUID.randomUUID() + "." + fileType;
        // TODO some_name.txt some_name(1).txt
    }

    private String getType(MultipartFile file) { // some_file.txt -> txt
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(
                        file.getOriginalFilename()
                                .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String fileName) {
        minio.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }








//    private final UserFilesRepository userFilesRepository;

    //    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(value = "UserFileService::GetByFileName", key = "#fileName")
//    public UserFile getByFileName(String fileName) {
//        return userFilesRepository
//                .findByFileName(fileName)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "File by filename: " + fileName + " not found")
//                );
//    }

//    @Override
//    @Transactional(readOnly = true)
//    //  @Cacheable(value = "UserFileService::getAllByNickName", key = "#nickName")
//    public List<UserFile> getAllByNickName(String nickName) {
//        return userFilesRepository.findAllUsersFilesByNickName(nickName);
//    }

//    @Override
//    @Transactional
//    @CachePut(value = "UserFileService::GetByFileName", key = "#userFile.fileName")
//    public UserFile updateFile(UserFile userFile) {
//        userFilesRepository.save(userFile);
//        return userFile;
//    }

//    @Override
//    @Transactional
//    @Cacheable(value = "UserFileService::GetByFileName", key = "#userFile.fileName")
//    public UserFile createFile(UserFile userFile, String nickName) {
//        UserEntity user = userService.getByNickName(nickName);
//        user.getUsersFiles().add(userFile);
//        userService.updateUser(user);
////        userFilesRepository.save(userFile);
//        return userFile;
//    }

//    @Override
//    @Transactional
//    @CacheEvict(value = "UserFileService::GetByFileName", key = "#fileName")
//    public void deleteByFileName(String fileName) {
//        userFilesRepository.deleteByFileName(fileName);
//    }
}
