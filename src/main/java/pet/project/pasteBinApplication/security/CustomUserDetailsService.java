package pet.project.pasteBinApplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.security.JwtEntityFactory;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        UserEntity userEntity = userService
                .getByNickName(nickName);

        return JwtEntityFactory.create(userEntity);
    }
}
