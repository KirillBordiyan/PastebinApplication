package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;
import pet.project.pasteBinApplication.web.dto.fileRequest.FileGetRequest;
import pet.project.pasteBinApplication.web.dto.fileRequest.FilePutRequest;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;

import java.util.List;

public interface FileService {

    FilePutResponse generateBucketFileName(FilePutRequest filePutRequest);
    List<UserFileData> getAllUserFiles(String nickName);
    FileGetResponse getPressignedGetUrl(FileGetRequest fileGetRequest);
    FilePutResponse getPressignedPutUrl(FilePutRequest bucketFileName);

}
