package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.List;
import java.util.UUID;

public interface UsersFilesService {

    UserFile getByFileName(String fileName);
    List<UserFile> getAllByNickName(String nickName);
    UserFile updateFile(UserFile userFile);
    UserFile createFile(UserFile userFile, String nickName);

    void deleteFile(Long id);

}
