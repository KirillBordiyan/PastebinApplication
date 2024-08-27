package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.security.JwtService;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.file.UserFileDataDto;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;
import pet.project.pasteBinApplication.web.mappers.UserFileMapper;
import pet.project.pasteBinApplication.web.mappers.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API part")
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final JwtService jwt;

    private final UserMapper userMapper;
    private final UserFileMapper fileMapper;

    @Operation(
            summary = "Update user",
            description = "With UserDto (request/response)"
    )
    @PreAuthorize("@CustomSecurityExpression.canAccessUserOnly(#dto.nickName)") //1
    @PutMapping
    public UserDto update(@Validated(OnUpdateProcess.class) @RequestBody UserDto dto) {
        UserEntity userEntity = userMapper.toEntity(dto);
        UserEntity updated = userService.updateUser(userEntity);
        return userMapper.toDto(updated);
    }

    @Operation(
            summary = "Get userDto by nick name"
    )
    @GetMapping("/{nickName}")
    public UserDto getByNickName(@PathVariable(name = "nickName") String nickName) {
        UserEntity userEntity = userService.getByNickName(nickName);
        return userMapper.toDto(userEntity);
    }

    @Operation(
            summary = "Delete user by nick name"
    )
    @PreAuthorize("@CustomSecurityExpression.canAccessUserOrAdmin(#nickName)") //2
    @DeleteMapping("/{nickName}")
    public void deleteByNickName(@PathVariable(name = "nickName") String nickName) {
        userService.deleteUserByNickName(nickName);
    }

    @Operation(
            summary = "Get all users files (dto) by user nick name"
    )
    @GetMapping("/{nickName}/files")
    public List<UserFileDataDto> getFilesByUserNickName(@PathVariable(name = "nickName") String nickName) {
        List<UserFileData> files = fileService.getAllUserFiles(nickName);
        return fileMapper.toDto(files);
    }

    @Operation(
            summary = "Get userDto by nick name"
    )
    @GetMapping("/who-am-i")
    public UserDto whoIAm(@RequestHeader(name = "Authorization") String access) {
        String token = access.substring(7);
        String name = jwt.getUserNickName(token);

        UserEntity userEntity = userService.getByNickName(name);
        return userMapper.toDto(userEntity);
    }
}
