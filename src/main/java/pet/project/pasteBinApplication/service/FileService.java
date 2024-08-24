package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFileData;

import java.util.List;

public interface FileService {
    List<UserFileData> getAllUserFiles(String nickName);
    String generateResultFileName(UserFileData fileData);
    String getResultFileLink(String bucketFileName);

}
