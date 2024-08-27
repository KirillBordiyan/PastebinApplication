package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.web.dto.fileRequest.FileGetRequest;
import pet.project.pasteBinApplication.web.dto.fileRequest.FilePutRequest;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File API part")
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "Generate PUT presigned url",
            description = "With UserDto (request/response)"
    )
    @PostMapping("/generate-put-url")
    public FilePutResponse generatePresignedPutUrl(@RequestBody FilePutRequest filePutRequest) {
        return fileService.getPressignedPutUrl(filePutRequest);
    }

    @Operation(
            summary = "Generate GET presigned url",
            description = "With UserDto (request/response)"
    )
    @PostMapping("/generate-get-url")
    public FileGetResponse generatePresignedGetUrl(@RequestBody FileGetRequest fileGetRequest) {
        return fileService.getPressignedGetUrl(fileGetRequest);
    }
}
