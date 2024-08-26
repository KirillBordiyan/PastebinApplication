package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;

import java.util.List;

public interface FileService {
    List<UserFileData> getAllUserFiles(String nickName);
    String generateResultFileName(UserFileData fileData);
    FileGetResponse getPressignedGetUrl(String bucketFileName);

    FilePutResponse getPressignedPutUrl(String bucketFileName);

}
