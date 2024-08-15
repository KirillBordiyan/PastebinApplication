package pet.project.pasteBinApplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.exceptions.AccessDeniedException;
import pet.project.pasteBinApplication.model.user.Role;
import pet.project.pasteBinApplication.model.user.UserEntity;
import pet.project.pasteBinApplication.prop.JwtProperties;
import pet.project.pasteBinApplication.service.UserService;
import pet.project.pasteBinApplication.web.dto.auth.JwtResponse;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(properties.getSecret());
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generatedAccessToken(UUID userId, String login, Set<Role> roles) { //метод генерации токена
        Map<String, String> claims = new HashMap<>();
        claims.put("authorities", getRoles(roles).toString());
        claims.put("id", String.valueOf(userId));

        return Jwts.builder()
                .claims(claims)
                .subject(login)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(properties.getAccess())))
                .signWith(generateKey())
                .compact();
    }


    public String generateRefreshToken(UUID userId, String login){
        Map<String, String> claims = new HashMap<>();
        claims.put("id", String.valueOf(userId));

        return Jwts.builder()
                .claims(claims)
                .subject(login)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(properties.getRefresh())))
                .signWith(generateKey())
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken){
        JwtResponse response = new JwtResponse();
        if(isTokenValid(refreshToken)){
            throw new AccessDeniedException();
        }
        UUID id = getUserId(refreshToken);
        UserEntity user = userService.getById(id);

        response.setUserId(id);
        response.setLogin(user.getLogin());
        response.setAccess(generatedAccessToken(id, user.getLogin(), user.getRoles()));
        response.setRefresh(generateRefreshToken(id, user.getLogin()));

        return response;
    }

    private List<String> getRoles(Set<Role> roles){
        return roles.stream().map(Role::getRoleName)
                .collect(Collectors.toList());
    }


    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private UUID getUserId(String jwt){
        Claims claims = getClaims(jwt);
        return UUID.fromString((String) claims.get("id"));
//        return Long.valueOf((String) claims.get("id"));
    }

    public boolean isTokenValid(String jwt){
        Claims claims = getClaims(jwt);
        return !claims.getExpiration().after(Date.from(Instant.now()));
    }

    public String extractUsername(String jwt){
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    public Authentication getAuthentication(String jwt){
        String login = extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
