package com.jwt.example.jwtExample.Auth;

import com.jwt.example.jwtExample.Entity.User;
import com.jwt.example.jwtExample.Repository.UserRepository;
import com.jwt.example.jwtExample.config.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
//        System.out.println("name ==>> " + request.getName());
//        System.out.println("email ==>> " + request.getEmail());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(AuthRequest request) {
        System.out.println("login password ==>> " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
