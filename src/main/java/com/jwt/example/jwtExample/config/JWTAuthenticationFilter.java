package com.jwt.example.jwtExample.config;

import com.jwt.example.jwtExample.Entity.User;
import com.jwt.example.jwtExample.Repository.UserRepository;
import com.jwt.example.jwtExample.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);

        // extract user email from JWT token
        userEmail = jwtService.extractUsername(jwt);
        System.out.println("doFilterInternal userEmail ==>> " + userEmail);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = this.userService.getUserByEmail(userEmail);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//            System.out.println("userDetails email ==>> " + userDetails.getUsername());
//            System.out.println("doFilterInternal userEmail ==>> " + user.getEmail());
            if(jwtService.isTokenValid(jwt, user)){
                System.out.println("doFilterInternal userEmail ==>> " + user);
                var authUser = new User();
                authUser.setEmail(user.getEmail());
                authUser.setPassword(user.getPassword());
                // If the JWT token is valid, it creates an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authUser,
                        null,
                        authUser.getAuthorities()
                );

                // It'll set authentication token details, including web authentication details, based on the HTTP request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                System.out.println("Auth Token ==>> " + authToken);

                // Update security context holder with the newly created authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            // ESSENTIAL. So that the next filters can be executed
            filterChain.doFilter(request, response);
        }
    }
}
