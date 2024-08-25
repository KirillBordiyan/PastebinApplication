package pet.project.pasteBinApplication.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.AuthService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.mappers.UserMapper;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthPageController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;


    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new UserDto());
        return "register.html";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto dto){
        UserEntity user = userMapper.toEntity(dto);
        UserEntity created = userService.createUser(user);
        return "redirect:/auth/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute JwtRequest loginRequest){
        JwtResponse login = authService.login(loginRequest);
        UserEntity user = userService.getByEmail(loginRequest.getEmail());

        return "redirect:/users/"+user.getNickName();//FIXME что тут делать? ретурн на ошибку?
    }



}
