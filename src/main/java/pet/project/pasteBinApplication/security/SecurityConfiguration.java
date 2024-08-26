package pet.project.pasteBinApplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pet.project.pasteBinApplication.prop.FrontendProperties;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class SecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;
    private final FrontendProperties frontendProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(management ->
                    management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/auth/**").permitAll();
                    request.requestMatchers("/swagger/**").permitAll();

                    request.requestMatchers("/auth/**").permitAll();
                    request.requestMatchers("/users/**").authenticated();

                    request.anyRequest().authenticated();

                })
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getWriter().write("UNAUTHORIZED --> Secured Level");
                    });
                    handling.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.getWriter().write("UNAUTHORIZED --> FORBIDDEN");
                    });
                })
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public WebMvcConfigurer corsConfigure(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*"); /*+ frontendProperties.getHost() + ":" + frontendProperties.getPort())*/;
            }
        };
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
