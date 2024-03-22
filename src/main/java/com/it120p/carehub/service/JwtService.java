package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.User;
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
        return extractClaim(token, Claims::getExpiration).after(new Date());
    }

    public boolean isTokenAllowedByUser(String email, String token) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) return false;
        return true;

        // Perform Token Validation Checks
        //List<String> updatedAllowedTokens = new ArrayList<>();
        //List<String> allowedTokens = optionalUser.get().getLogState().getAllowedTokens();
        //allowedTokens.forEach(allowedToken -> {
            // Check if token is still valid
        //    if (isTokenValid(allowedToken)) {
        //        updatedAllowedTokens.add(allowedToken);
        //    }
        //});

        // Update Allowed Tokens
        //optionalUser.get().getLogState().setAllowedTokens(updatedAllowedTokens);
        //userRepository.save(optionalUser.get());

        // Find Token
        //return updatedAllowedTokens.contains(token);
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
