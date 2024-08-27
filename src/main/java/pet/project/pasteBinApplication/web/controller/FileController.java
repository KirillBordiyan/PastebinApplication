package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.web.dto.fileRequest.FileGetRequest;
import pet.project.pasteBinApplication.web.dto.fileRequest.FilePutRequest;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File API part")
@Validated
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )
    @PostMapping
    public FilePutResponse generatePresignedPutUrl(@RequestBody FilePutRequest filePutRequest) {
        return fileService.getPressignedPutUrl(filePutRequest);
    }

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )//нельзя POST, тк конфликт методов
    @GetMapping //post = body, get = param
    public FileGetResponse generatePresignedGetUrl(@RequestBody FileGetRequest fileGetRequest) {
        return fileService.getPressignedGetUrl(fileGetRequest);
    }
}
