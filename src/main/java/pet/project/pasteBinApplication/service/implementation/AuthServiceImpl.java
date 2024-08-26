package pet.project.pasteBinApplication.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.security.JwtService;
import pet.project.pasteBinApplication.service.AuthService;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse response = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserEntity user = userService.getByEmail(loginRequest.getEmail());

        response.setAccess(jwtService.generatedAccessToken(user.getNickName(), user.getRoles()));
        response.setRefresh(jwtService.generateRefreshToken(user.getNickName()));

        return response;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtService.refreshUserTokens(refreshToken);
    }
}
