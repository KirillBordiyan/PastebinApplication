package pet.project.pasteBinApplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login=" + login + " not found!"));
        return new User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                getRole(userEntity));
    }

    private List<SimpleGrantedAuthority> getRole(UserEntity client) {
        List<String> clientRoles = client.getRoles().stream().map(Role::toString).toList();
        return clientRoles.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
