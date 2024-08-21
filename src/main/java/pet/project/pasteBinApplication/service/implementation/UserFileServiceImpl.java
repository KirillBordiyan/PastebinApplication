package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.repositories.UserRepository;
import pet.project.pasteBinApplication.repositories.UserFilesRepository;
import pet.project.pasteBinApplication.service.UsersFilesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UsersFilesService {

    private final UserRepository userRepository;
    private final UserFilesRepository userFilesRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserFileService=getByFileName", key = "#fileName")
    public UserFile getByFileName(String fileName) {
        return userFilesRepository
                .findByFileName(fileName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "File by filename: " + fileName + " not found")
                );
    }

    @Override
    @Transactional(readOnly = true)
  //  @Cacheable(value = "UserFileService::getAllByNickName", key = "#nickName")
    public List<UserFile> getAllByNickName(String nickName) {
        return userFilesRepository.findAllUsersFilesByNickName(nickName);
    }

    @Override
    @Transactional
    @CachePut(value = "UserFileService=getByFileName", key = "#userFile.fileName")
    public UserFile updateFile(UserFile userFile) {
        userFilesRepository.save(userFile);
        return userFile;
    }

    @Override
    @Transactional
    @Cacheable(value = "UserFileService=getByFileName", key = "#userFile.fileName")
    public UserFile createFile(UserFile userFile, String nickName) {
        userFilesRepository.save(userFile);
        return userFile;
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserFileService=getByFileName", key = "#fileName")
    public void deleteByFileName(String fileName) {
        userFilesRepository.deleteByFileName(fileName);
    }
}
