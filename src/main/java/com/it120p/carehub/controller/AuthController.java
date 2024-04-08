package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.AlreadyExistException;
import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.dto.UserLoginDTO;
import com.it120p.carehub.model.dto.UserInfoDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.AuthService;
import com.it120p.carehub.service.ResourceService;
import com.it120p.carehub.service.UserService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;


    @PostMapping("/register")
    public UserInfoDTO registerAccount(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "contactNo") String contactNo,
            @RequestParam(name = "birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestPart(name = "profilePic", required = false) MultipartFile profilePic
    ) throws Exception {

        // Check if email is unique
        if (userService.isUserExistByEmail(email)) {
            throw new AlreadyExistException("Email");
        }

        // Initial Registration of email
        User newUser = authService.registerUser(email, password);

        // Update User
        newUser.setName(name);
        newUser.setContactNo(contactNo);
        newUser.setBirthDate(birthDate);
        if (profilePic != null) newUser.setPhotoResource( resourceService.setUserResource(profilePic) );
        User updateUser = userService.updateUser(newUser);

        // Return DTO
        return UserInfoDTO.fromUser(updateUser);
    }

    @PostMapping("/login")
    public UserLoginDTO loginAccount(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password
    ) throws Exception {

        // Check if email exists
        if (!userService.isUserExistByEmail(email)) {
            throw new MissingException("Email");
        }

        // Login user+email
        String jwtToken = authService.loginUser(email, password);

        // Check if login successful
        if (jwtToken.isEmpty()) {
            throw new AuthenticationException("User unable to authenticate");
        }

        // Return UserLoginResponseDTO
        return UserLoginDTO.builder()
                .email(email)
                .token(jwtToken)
                .build();
    }

    @PostMapping(path = "/logout")
    public void logoutUser(Authentication authentication) {

        // Logout User
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        String token = (String) authentication.getDetails();

        authService.logoutUser(email, token);
    }
}
