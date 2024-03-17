package com.domain.library.security.auth;

import com.domain.library.security.entity.Token;
import com.domain.library.security.entity.User;
import com.domain.library.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public Token register(@RequestBody User user){
        return authenticationService.register(user);
    }
    @PostMapping("/login")
    public Token login(@RequestBody User user){
        return authenticationService.login(user);
    }
}
