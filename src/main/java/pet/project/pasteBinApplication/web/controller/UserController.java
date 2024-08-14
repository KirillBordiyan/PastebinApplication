package pet.project.pasteBinApplication.web.controller;

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


    @PutMapping
    public UserDto update(@Validated(OnUpdateProcess.class) @RequestBody UserDto dto){
        UserEntity userEntity = userMapper.toEntity(dto);
        UserEntity updated = userService.updateUser(userEntity);
        return userMapper.toDto(updated);
    }

//    @GetMapping("/{id}")
    @GetMapping("/{login}")
    public UserDto getById(/*@PathVariable(name = "id") UUID id*/ @PathVariable String login){
//        UserEntity userEntity = userService.getById(id);
        UserEntity userEntity = userService.getByLogin(login);
        return userMapper.toDto(userEntity);
    }

//    @DeleteMapping("/{id}")
    @DeleteMapping("/{login}")
    public void deleteById(/*@PathVariable(name = "id") UUID id*/ @PathVariable String login){
        userService.deleteUser(login);
    }

    @GetMapping("/{login}/files")
    public List<UsersFileDto> getFilesByUserId(@PathVariable(name = "login") String login){
        List<UserFile> files = filesService.getAllByLogin(login);
        return fileMapper.toListDto(files);
    }

    @PostMapping("/{login}/files")
    public UsersFileDto createFile(@PathVariable(name = "login") String login,
                                   @Validated(OnCreateProcess.class) @RequestBody UsersFileDto dto){
        UserFile file = fileMapper.toEntity(dto);
        UserEntity userEntity = userService.getByLogin(login);
        UserFile createdFile = filesService.createFile(file, userEntity.getUserId());
        return fileMapper.toDto(createdFile);
    }
}
