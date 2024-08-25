package pet.project.pasteBinApplication.ui.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.model.file.UserFileData;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.FileService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.ui.service.FilePageService;

import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "File page controller", description = "Visual app's part ")
public class FilePageController {

    private final UserService userService;
    private final FileService fileService;
    private final FilePageService filePageService;

    @GetMapping("/{nickName}")
    public String getUserByNickName(Model model, @PathVariable(name = "nickName") String nickName){

        List<UserFileData> fileData = fileService.getAllUserFiles(nickName);
        model.addAttribute("fileData", fileData);
        model.addAttribute("nickName", nickName);
        return "user-nickname.html";
    }

    @GetMapping("/{nickName}/{bucketFileName}")
    public String downloadFileByFileName(@PathVariable(name = "nickName") String nickName,
                                         @PathVariable(name = "bucketFileName") String bucketFileName){
        String url = fileService.getResultFileLink(bucketFileName);

//FIXME дописать логику загрузки из minio

        return "redirect:/users/" + url;
    }

    @PostMapping("/{nickName}/create")
    public String createFile(@PathVariable(name = "nickName") String nickName,
                             @RequestParam String fileName,
                             @RequestParam String content){

        UserEntity user = userService.getByNickName(nickName);

        //создали ссылку и сохранили ее
        UserFileData file = new UserFileData();
        file.setOriginalFileName(fileName);
        file.setOwnerNickName(user);

        String resultFileName = fileService.generateResultFileName(file);

        //запустили процесс сохранения файла
        File toSaveFile = filePageService.createFile(nickName, resultFileName, content);
        //FIXME вопрос, нужно ли тут что-то возвращать из загруженного файла
        filePageService.upload(toSaveFile);

        return "redirect:/users/" + nickName;
    }
}
