package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.service.UsersFilesService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UsersFilesService {

    @Override
    public UserFile getByFileName(String fileName) {
        return null;
    }

    @Override
    public List<UserFile> getAllByLogin(String login) {
        return List.of();
    }

    @Override
    public UserFile updateFile(UserFile userFile) {
        return null;
    }

    @Override
    public UserFile createFile(UserFile userFile, UUID userId) {
        return null;
    }

    @Override
    public void deleteFile(Long id) {

    }
}
