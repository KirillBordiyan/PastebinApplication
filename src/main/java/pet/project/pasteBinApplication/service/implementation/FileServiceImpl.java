package pet.project.pasteBinApplication.service.implementation;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.PresignedObjectUrlCreatingException;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.repositories.FileDataRepository;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileDataRepository;
    private final UserService userService;
    private final MinioClient minioClient;
//    private final MinioProperties properties;


    @Override
    public List<UserFileData> getAllUserFiles(String nickName) {
        return List.copyOf((userService.getByNickName(nickName).getFiles()));
    }

    @Override
    @Transactional
    public String generateResultFileName(UserFileData fileData) {
        //put
        //приходит some_name вместо some_name.txt
        //тогда будет user_some_name_1
        String search = fileData.getOwnerNickName().getNickName() + "_" + fileData.getOriginalFileName();
        String resultFileName = search;

        if (fileDataRepository.findByBucketFileName(search).isPresent()) {
            int i = 1;
            //меняем имя файла
            while (true) {
                if (fileDataRepository.findByBucketFileName(search + "_" + i++).isEmpty()) {
                    break;
                }
            }
            fileData.setOriginalFileName(fileData.getOriginalFileName() + "_" + i);
            resultFileName = search + "_" + i;
        }

        fileData.setBucketFileName(resultFileName);
        UserFileData savingFileData = fileDataRepository.save(fileData);

        UserEntity user = userService.getByNickName(fileData.getOwnerNickName().getNickName());
        user.getFiles().add(savingFileData);
        userService.updateUser(user);

        return resultFileName;
    }

    @Override
    public FilePutResponse getPressignedPutUrl(String bucketFileName) {
        try {
            FilePutResponse response = new FilePutResponse();

//            Map<String, String> reqParams = new HashMap<String, String>();
//            reqParams.put("response-content-type", "application/json");

            String presignedPutUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket("bucket1")
                            .object(bucketFileName)
                            .expiry(60 * 60 * 24)
//                            .extraQueryParams(reqParams)
                            .build());

            response.setUniqueFileName(bucketFileName);
            response.setPresignedPutUrl(presignedPutUrl);

            return response;

        } catch (Exception e) {
            throw new PresignedObjectUrlCreatingException("Pre-signed PUT link creating was failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileGetResponse getPressignedGetUrl(String bucketFileName) {
        try {
            FileGetResponse response = new FileGetResponse();
            String presignedGetUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("bucket1")
                            .object(bucketFileName)
                            .expiry(24 * 60 * 60)
                            .build()
            );

            response.setUniqueFileName(bucketFileName);
            response.setPresignedGetUrl(presignedGetUrl);

            return response;

        } catch (Exception e) {
            throw new PresignedObjectUrlCreatingException("Pre-signed GET link creating was failed: " + e.getMessage());
        }
    }


//    private final MinioClient minio;
//    private final MinioProperties minioProperties;
//
//    @Override
//    public String upload(UserFile toUploadFile) {
//        try {
//            createBucket();
//        } catch (Exception e) {
//            throw new FileUploadException("File upload failed: " + e.getMessage());
//        }
//
//        MultipartFile file = toUploadFile.getFile();
//        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
//            throw new FileUploadException("File upload failed: file is empty or must have name");
//        }
//
//        String fileName = generateFileName(file);
//        InputStream inputStream;
//
//        try {
//            inputStream = file.getInputStream();
//        } catch (Exception e) {
//            throw new FileUploadException("File upload failed: " + e.getMessage());
//        }
//
//        saveFile(inputStream, fileName);
//        return fileName;
//    }
//
//    @SneakyThrows
//    private void createBucket() {
//        boolean exists = minio.bucketExists(BucketExistsArgs.builder()
//                .bucket(minioProperties.getBucket())
//                .build());
//        if (!exists) {
//            minio.makeBucket(MakeBucketArgs.builder()
//                    .bucket(minioProperties.getBucket())
//                    .build());
//        }
//    }
//
//    private String generateFileName(MultipartFile file) {
//        String fileType = getType(file);
//        return UUID.randomUUID() + "." + fileType;
//        // TODO some_name.txt some_name(1).txt
//    }
//
//    private String getType(MultipartFile file) { // some_file.txt -> txt
//        return Objects.requireNonNull(file.getOriginalFilename())
//                .substring(
//                        file.getOriginalFilename()
//                                .lastIndexOf(".") + 1);
//    }
//
//    @SneakyThrows
//    private void saveFile(InputStream inputStream, String fileName) {
//        minio.putObject(PutObjectArgs.builder()
//                .stream(inputStream, inputStream.available(), -1)
//                .bucket(minioProperties.getBucket())
//                .object(fileName)
//                .build());
//    }


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
