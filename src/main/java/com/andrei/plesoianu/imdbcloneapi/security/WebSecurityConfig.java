package com.andrei.plesoianu.imdbcloneapi.security;

import com.andrei.plesoianu.imdbcloneapi.enums.AppRole;
import com.andrei.plesoianu.imdbcloneapi.models.Role;
import com.andrei.plesoianu.imdbcloneapi.models.User;
import com.andrei.plesoianu.imdbcloneapi.repositories.RoleRepository;
import com.andrei.plesoianu.imdbcloneapi.repositories.UserRepository;
import com.andrei.plesoianu.imdbcloneapi.security.jwt.AuthEntryPointJwt;
import com.andrei.plesoianu.imdbcloneapi.security.jwt.AuthTokenFilter;
import com.andrei.plesoianu.imdbcloneapi.security.services.UserDetailsServiceImpl;
import lombok.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public WebSecurityConfig(@NonNull UserDetailsServiceImpl userDetailsService,
                             @NonNull AuthEntryPointJwt unauthorizedHandler,
                             @NonNull RoleRepository roleRepository,
                             @NonNull UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/v2/api-docs/**").permitAll()
                        .requestMatchers("/configuration/ui/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/configuration/security/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .authenticationProvider(authenticationProvider())
                .csrf(CsrfConfigurer::disable) // Disable CSRF for simplicity (or restrict more selectively)
                .addFilterBefore(
                    authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData() {
        return (args -> {
            // Add roles to DB
            var userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                   .orElseGet(() -> {
                      var role = new Role();
                      role.setRoleName(AppRole.ROLE_USER);
                      return roleRepository.save(role);
                   });

            var adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        var role = new Role();
                        role.setRoleName(AppRole.ROLE_ADMIN);
                        return roleRepository.save(role);
                    });

            // Add one user to the DB, for each Role
            if (userRepository.count() == 0) {
                var user1 = new User();
                user1.setEmail("client@mail.com");
                user1.setPassword(passwordEncoder().encode("user1"));
                user1.setUsername("user1");
                user1.setRoles(Set.of(userRole));
                userRepository.save(user1);

                var user2 = new User();
                user2.setEmail("admin@mail.com");
                user2.setPassword(passwordEncoder().encode("user2"));
                user2.setUsername("user2");
                user2.setRoles(Set.of(adminRole));
                userRepository.save(user2);
            }
        });
    }
}
