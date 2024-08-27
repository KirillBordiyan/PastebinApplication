package pet.project.pasteBinApplication.security.expressions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.repositories.RoleRepository;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.security.JwtEntity;

@Service("CustomSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public boolean canAccessUserOrAdmin(String nickName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();
        String userNickName = user.getNickName();
        Role roleAdmin = roleRepository.findByRoleName("ADMIN");

        return userNickName.equals(nickName) || hasRole(auth, roleAdmin);
    }

    public boolean canAccessUserOnly(String nickName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();

        return user.getNickName().equals(nickName);
    }

    public boolean canAccessFileUserOrAdmin(String fileName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();
        Role roleAdmin = roleRepository.findByRoleName("ADMIN");

        return userService.isUserFileOwner(user.getNickName(), fileName) || hasRole(auth, roleAdmin);
    }

    public boolean canAccessFileUserOnly(String fileName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();

        return userService.isUserFileOwner(user.getNickName(), fileName);
    }

    private boolean hasRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }
}
