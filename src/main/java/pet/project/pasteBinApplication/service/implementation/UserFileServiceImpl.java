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
import java.util.UUID;

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
    public List<UserFile> getAllByLogin(String login) {
//        return null;
        return usersFilesRepository.findAllUsersFilesByLogin(login);
    }

    @Override
    @Transactional
    public UserFile updateFile(UserFile userFile) {
        usersFilesRepository.save(userFile);
        return userFile;
    }

    @Override
    @Transactional
    public UserFile createFile(UserFile userFile, UUID userId) {
        usersFilesRepository.save(userFile);
        //todo
//        usersFilesRepository.assignToUserById(userFile.getId(), userId);
        return userFile;
    }

    @Override
    @Transactional
    public void deleteFile(Long id) {
        usersFilesRepository.deleteById(id);
    }
}
