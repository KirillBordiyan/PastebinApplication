package pet.project.pasteBinApplication.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.AuthService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.service.implementation.UserServiceImpl;
import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.mappers.UserMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreateProcess.class) @RequestBody UserDto userDto){
        UserEntity user = userMapper.toEntity(userDto);
        UserEntity created = userService.createUser(user);
        return userMapper.toDto(created);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
