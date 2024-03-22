package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserAuthToken;
import com.it120p.carehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

}
