package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserAuthToken;
import com.it120p.carehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerUser(String email, String password) {
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        user.setUserAuthTokens(new ArrayList<>());
        user.getUserAuthTokens().add(new UserAuthToken());

        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        String token = "";

        // Attempt Authentication
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password
                    )
            );

            // Create JWT
            token = jwtService.generateToken(
                    (UserDetails) authentication.getPrincipal()
            );

            // Add created token to allowedTokens
            Optional<User> optLoggedUser = userRepository.findUserByEmail(email);

            if (optLoggedUser.isEmpty()) throw new Exception("User does not exist!");

            optLoggedUser.get().getUserAuthTokens().add(new UserAuthToken(token));
            userRepository.save(optLoggedUser.get());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return token;
    }

    public boolean logoutUser(String email, String token) {
        // Remove token from allowedTokens
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) return false;

        optionalUser.get().getUserAuthTokens().remove(new UserAuthToken(token));
        userRepository.save(optionalUser.get());
        return true;
    }

}
