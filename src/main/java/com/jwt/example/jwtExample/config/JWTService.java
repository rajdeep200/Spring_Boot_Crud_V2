package com.jwt.example.jwtExample.config;

import com.jwt.example.jwtExample.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "JtDGA9fJgoVft9J1IX+tlKyP3JcoKJyZdvwhAynziZ51z7CkNvVMmY+DivYvWiNdWbFsoHyxEX+5oFeLk7heBrbIwqC4/rsNDgr/iE++Ln8ourI1Q6mNpKvs3GeUqJ2TXXr3M+3r7tXAVCX0PLNpZE3CBJBREx22HUMJAKSULeSDxBJRjFxxMcBQntmAfARvqE1TO1TgRmkmzMOyC7x/1Vz91h8V7r9fUKAUO/G59uz8J9rWWzAge4m/tbLzdLhw8UEL+jFLL4T81VwiiVRXYFOM1yz6fnL3RHNay2XYH8oia352LHh5toxZJwNsG+O2C8kA9VKgJ0eXLiYmAWUtCQ==";

    // extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Subject should be the username or email of the user
    }

    // generate token with UserDetails only
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // generate token with extra claims
    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
//        System.out.println("Jwt Username ==>> " + userDetails.getEmail());
        return Jwts
                .builder() // The 'builder' method creates a builder object that allows you to configure and build a jwt
                .setClaims(extraClaims) // This sets the claims (payload) of the JWT using the extraClaims parameter
                .setSubject(userDetails.getEmail()) // Sets the subject of the JWT. In this case, it's setting the subject to the username obtained from the userDetails object.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the "issued at" (iat) claim of the JWT, indicating when the token was issued.
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)) // Sets the expiration time of the JWT. It's set to be one minute (60,000 milliseconds) after the current time.
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Signs the JWT using a signing key obtained from the getSignInKey() method (not shown in the provided code). It specifies the algorithm used for signing the token, which is HMAC SHA-256 (HS256) in this case.
                .compact(); // It will build the JWT and return it as a compact string representation
    }

    // check is token valid
    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

    // check if token expired
    private boolean isTokenExpired(String token) {
        return extractTokenExpiration(token).before(new Date());
    }

    // check token expiration date
    private Date extractTokenExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // extract single claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // get signing key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
