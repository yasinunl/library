package com.domain.library.security.config;

import com.domain.library.security.auth.Permission;
import com.domain.library.security.entity.Role;
import com.domain.library.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/students/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/borrowings/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/books/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.POST, "/books/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.DELETE, "/books/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/students/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.POST, "/students/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.DELETE, "/students/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/borrowings/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.POST, "/borrowings/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(HttpMethod.DELETE, "/borrowings/**").hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
