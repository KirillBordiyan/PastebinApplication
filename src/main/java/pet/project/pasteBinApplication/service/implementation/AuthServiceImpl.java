package pet.project.pasteBinApplication.service.implementation;

import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.service.AuthService;
import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }
}
