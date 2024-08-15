package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.repositories.UserRepository;
import pet.project.pasteBinApplication.repositories.UsersFilesRepository;
import pet.project.pasteBinApplication.service.UsersFilesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UsersFilesService {

    private final UserRepository userRepository;
    private final UsersFilesRepository usersFilesRepository;

    @Override
    @Transactional(readOnly = true)
    public UserFile getByFileName(String fileName) {
        return usersFilesRepository
                .findByFileName(fileName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "File by filename: " + fileName + " not found")
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFile> getAllByNickName(String nickName) {
//        return null;
        return usersFilesRepository.findAllUsersFilesByNickName(nickName);
    }

    @Override
    @Transactional
    public UserFile updateFile(UserFile userFile) {
        usersFilesRepository.save(userFile);
        return userFile;
    }

    @Override
    @Transactional
    public UserFile createFile(UserFile userFile, String nickName) {
        usersFilesRepository.save(userFile);
        //todo метод создания файла
//        usersFilesRepository.assignToUserById(userFile.getId(), userId);
        return userFile;
    }

    @Override
    @Transactional
    public void deleteFile(Long id) {
        usersFilesRepository.deleteById(id);
    }
}
