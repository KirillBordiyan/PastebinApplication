package pet.project.pasteBinApplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.AuthService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;
import pet.project.pasteBinApplication.web.dto.user.UserDto;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.mappers.UserMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
@Tag(name = "Auth controller", description = "Auth API part")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Login",
            description = "Just login"
    )
    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(
            summary = "Register",
            description = "Register new user in application"
    )
    @PostMapping("/register")
    public UserDto register(@Validated(OnCreateProcess.class) @RequestBody UserDto userDto) {
        UserEntity user = userMapper.toEntity(userDto);
        UserEntity created = userService.createUser(user);
        return userMapper.toDto(created);
    }

    @Operation(
            summary = "Refresh",
            description = "Refresh all tokens by actual refresh token"
    )
    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestHeader(name = "X-Refresh") String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
