package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;
import pet.project.pasteBinApplication.web.mappers.UserFileMapper;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File API part")
public class FileController {

    private final FileService fileService;
    private final UserFileMapper fileMapper;

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )
    @PostMapping
    public String generateFileLink(@RequestBody UserFileDataDto fileDataDto) {//FIXME может и @RequestBody
        return fileService.generateResultFileName(fileMapper.toEntity(fileDataDto));
        //String тк на фронте название файла будет заменено на вот это новое
    }

    @Operation(
            summary = "Generate unique file name",
            description = "With UserDto (request/response)"
    )
    @GetMapping("/{bucketFileName}")
    public String getActualFileLink(@PathVariable(name = "bucketFileName") String bucketFileName/*@ModelAttribute UserFileDataDto fileDataDto*/){//FIXME может и @RequestBody
        return fileService.getResultFileLink(bucketFileName);
    }

}

