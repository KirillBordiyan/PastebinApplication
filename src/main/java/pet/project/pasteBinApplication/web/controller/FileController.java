package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;
import pet.project.pasteBinApplication.web.dto.fileResponse.FileGetResponse;
import pet.project.pasteBinApplication.web.dto.fileResponse.FilePutResponse;
import pet.project.pasteBinApplication.web.dto.validation.OnGetProcess;
import pet.project.pasteBinApplication.web.mappers.UserFileMapper;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File API part")
@Validated
public class FileController {

    private final FileService fileService;
    private final UserFileMapper fileMapper;

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )
    @PostMapping
    public FilePutResponse generatePresignedPutUrl(@Validated(OnGetProcess.class) @RequestBody UserFileDataDto fileDataDto) {
        //метод put ссылку - для загрузки
        //метод get ссылку - для скачивания

        String unique = fileService.generateResultFileName(fileMapper.toEntity(fileDataDto));
        return fileService.getPressignedPutUrl(unique);
    }

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )
    @GetMapping
//    public FileGetResponse generatePresignedGetUrl(@PathVariable(name = "bucketFileName") String bucketFileName){
    public FileGetResponse generatePresignedGetUrl(@RequestBody String bucketFileName){
        return fileService.getPressignedGetUrl(bucketFileName);
    }

}

