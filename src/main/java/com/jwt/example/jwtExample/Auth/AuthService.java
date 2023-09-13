package com.jwt.example.jwtExample.Auth;

import com.jwt.example.jwtExample.Entity.User;
import com.jwt.example.jwtExample.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        var savedUser = userRepository.save(user);

        return null;
    }

    public AuthResponse login(AuthRequest request) {
        return null;
    }
}
