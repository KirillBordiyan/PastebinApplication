package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;

import java.util.List;

public interface FileService {
    List<UserFileData> getAllUserFiles(String nickName);
    String generateResultFileLink(UserFileData fileData);
    String getResultFileLink(String bucketFileName);


}
