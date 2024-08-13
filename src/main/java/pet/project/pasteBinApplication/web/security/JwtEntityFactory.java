package pet.project.pasteBinApplication.web.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

    public static JwtEntity create(UserEntity userEntity){
        return new JwtEntity(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getLogin(),
                userEntity.getPassword(),
                toGrantedAuth(new ArrayList<>(userEntity.getRoles()))
        );
    }

    private static List<SimpleGrantedAuthority> toGrantedAuth(List<Role> roles){
        return roles.stream()
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
