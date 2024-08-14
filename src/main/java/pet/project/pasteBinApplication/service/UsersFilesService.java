package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.List;

public interface UsersFilesService {

    UserFile getByFileName(String fileName);
    List<UserFile> getAllByLogin(String login);
    UserFile updateFile(UserFile userFile);
    UserFile createFile(UserFile userFile, Long userId);

    void deleteFile(Long id);

}
