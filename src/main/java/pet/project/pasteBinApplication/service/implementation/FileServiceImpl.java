package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.repositories.FileDataRepository;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileDataRepository;
    private final UserService userService;


    @Override
    public List<UserFileData> getAllUserFiles(String nickName) {
        return List.copyOf(userService.getByNickName(nickName).getFiles());
    }

    @Override
    @Transactional
    public String generateResultFileLink(UserFileData fileData) {

        String search = fileData.getOwnerNickName() + "_" + fileData.getOriginalFileName();
        String resultFileLink = null;

        if (fileDataRepository.findByLinkedFileName(search).isPresent()) {
            int i = 1;
            //меняем линку
            while (true) {
                if (fileDataRepository.findByLinkedFileName(search + "_" + i++).isEmpty()) {
                    break;
                }
            }
            resultFileLink = search + "_" + i;
        }

        fileData.setLinkedFileName(resultFileLink);

        fileDataRepository.save(fileData);
        return resultFileLink;
    }

    //приходит some_name вместо some_name.txt
    //тогда будет user_some_name_1
    @Override
    @Transactional(readOnly = true)
    public String getResultFileLink(String bucketFileName) {
        UserFileData userFileData = fileDataRepository.findByLinkedFileName(bucketFileName)
                .orElseThrow(() -> new ResourceNotFoundException("File not found (by name)"));
        return userFileData.getLinkedFileName();
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
