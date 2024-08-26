package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.web.dto.auth.JwtRequest;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse refresh(String refreshToken);
    JwtResponse login(JwtRequest loginRequest);
}
