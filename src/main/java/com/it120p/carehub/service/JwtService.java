package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserAuthToken;
import com.it120p.carehub.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long tokenExpiration;

    @Autowired
    private UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(new HashMap<>())
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isTokenAllowedByUser(String email, String token) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) return false;

        // Perform Token Validation Checks
        List<UserAuthToken> updatedAllowedTokens = new ArrayList<>();
        List<UserAuthToken> allowedTokens = optionalUser.get().getUserAuthTokens();
        allowedTokens.forEach(allowedToken -> {
            // Check if token is still valid
            if (isTokenValid(allowedToken.getAuthToken())) {
                updatedAllowedTokens.add(allowedToken);
            }
        });

        // Update Allowed Tokens
        User user = optionalUser.get();
        user.setUserAuthTokens(updatedAllowedTokens);
        userRepository.save(user);

        // Find Token
        return updatedAllowedTokens.stream().anyMatch(
                userAuthToken -> userAuthToken.getAuthToken().equals(token)
        );
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
