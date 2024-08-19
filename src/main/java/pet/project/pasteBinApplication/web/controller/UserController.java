package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.model.file.UserFile;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.service.UsersFilesService;
import pet.project.pasteBinApplication.web.dto.file.UsersFileDto;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;
import pet.project.pasteBinApplication.web.mappers.UserMapper;
import pet.project.pasteBinApplication.web.mappers.UtilityFileMapper;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API part")
public class UserController {

    private final UserService userService;
    private final UsersFilesService filesService;
    private final UserMapper userMapper;
    private final UtilityFileMapper fileMapper;


    /*FIXME дописать как понадобятся методы
        чел делал для таски на обновление так:

        @PutMapping
        public TaskDto update(@Validated(OnUpdateProcess.class) @RequestBody TaskDto dto){
            Task task = taskMapper.toEntity(dto);
            Task updated = taskService.update(task);
            return taskMapper.toDto(updated);
        }
    */


    @Operation(
            summary = "Update user",
            description = "With UserDto (request/response)"
    )
    @PutMapping
    public UserDto update(@Validated(OnUpdateProcess.class) @RequestBody UserDto dto) {
        UserEntity userEntity = userMapper.toEntity(dto);
        UserEntity updated = userService.updateUser(userEntity);
        return userMapper.toDto(updated);
    }

    @Operation(
            summary = "Get userDto by nick name"
    )
    @GetMapping("/{nickName}") // -> @RequestBody UserAnotherDto dto (login)
    public UserDto getByLogin(/*@PathVariable(name = "id") UUID id*/ @PathVariable(name = "nickName") String nickName) {
        UserEntity userEntity = userService.getByNickName(nickName);
        return userMapper.toDto(userEntity);
    }

    @Operation(
            summary = "Delete user by nick name"
    )
    @DeleteMapping("/{nickName}")
    public void deleteByNickName(/*@PathVariable(name = "id") UUID id*/ @PathVariable(name = "nickName") String nickName) {
        userService.deleteUserByNickName(nickName);
    }

    @Operation(
            summary = "Get all users files (dto) by user nick name"
    )
    @GetMapping("/{nickName}/files")
    public List<UsersFileDto> getFilesByUserLogin(@PathVariable(name = "nickName") String nickName) {
        List<UserFile> files = filesService.getAllByNickName(nickName);
        return fileMapper.toListDto(files);
    }


    @Operation(
            summary = "Create fileDto"
    )
    @PostMapping("/{nickName}/files")
    public UsersFileDto createFile(@PathVariable(name = "nickName") String nickName,
                                   @Validated(OnCreateProcess.class) @RequestBody UsersFileDto dto) {
        UserFile file = fileMapper.toEntity(dto);
        UserEntity userEntity = userService.getByNickName(nickName);
        UserFile createdFile = filesService.createFile(file, userEntity.getNickName());
        return fileMapper.toDto(createdFile);
    }
}
