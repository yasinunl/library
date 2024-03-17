package com.domain.library.security.service;

import com.domain.library.security.config.SecurityAppConfig;
import com.domain.library.security.entity.Role;
import com.domain.library.security.entity.Token;
import com.domain.library.security.entity.User;
import com.domain.library.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Token register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() != Role.USER && user.getRole() != Role.ADMIN) user.setRole(Role.USER);
        user.setRole(user.getRole());
        userRepo.save(user);
        String token = jwtService.generateToken(user.getEmail());
        Token role = new Token();
        role.setToken(token);
        role.setRole(user.getRole());
        return role;
    }
    public Token login(User user){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        User user1 = userRepo.findByEmail(user.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user1.getEmail());
        Token role = new Token();
        role.setToken(token);
        role.setRole(user1.getRole());
        return role;
    }
}
