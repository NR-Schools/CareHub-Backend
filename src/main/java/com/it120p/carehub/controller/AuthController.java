package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.AlreadyExistException;
import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.dto.UserLoginDTO;
import com.it120p.carehub.model.dto.VerificationStatusDTO;
import com.it120p.carehub.model.dto.UserInfoDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserServiceCare;
import com.it120p.carehub.service.AuthService;
import com.it120p.carehub.service.ResourceService;
import com.it120p.carehub.service.UserService;
import com.it120p.carehub.service.VerificationService;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private VerificationService verificationService;


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

        // Send Verification Code
        verificationService.sendVerificationCode(updateUser);

        // Return DTO
        return UserInfoDTO.fromUser(updateUser);
    }

    @PostMapping("/register/customer")
    public UserInfoDTO registerCustomer(
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

        // Send Verification Code
        verificationService.sendVerificationCode(updateUser);

        // Return DTO
        return UserInfoDTO.fromUser(updateUser);
    }

    @PostMapping("/register/provider")
    public UserInfoDTO registerServiceProvider(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "contactNo") String contactNo,
            @RequestParam(name = "birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestPart(name = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam("type") String type,
            @RequestParam("description") String description,
            @RequestParam("offerings") List<String> offerings
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

        // Set Service
        UserServiceCare userServiceCare = UserServiceCare.builder()
                .type(type)
                .description(description)
                .offerings(offerings)
                .build();
        newUser.setUserServiceCare(userServiceCare);
        User updateUser = userService.updateUser(newUser);

        // Send Verification Code
        verificationService.sendVerificationCode(updateUser);

        // Return DTO
        return UserInfoDTO.fromUser(updateUser);
    }

    @GetMapping("/user/verify")
    public VerificationStatusDTO verifyUser(
            @RequestParam("email") String email,
            @RequestParam("code") String code
        ) {
            // Get user from email
            User user = userService.getUserByEmail(email);

            // Check if code is correct
            boolean status = verificationService.attemptVerifyCode(user, code);

            // Return verification
            return VerificationStatusDTO.builder()
                    .email(email)
                    .isVerifySuccessful(status)
                    .build();
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

        // Check if customer or provider
        String userType = "Provider";
        UserServiceCare userServiceCare = userService.getUserByEmail(email).getUserServiceCare();
        if (userServiceCare == null) userType = "Customer";

        // Check if login successful
        if (jwtToken.isEmpty()) {
            throw new AuthenticationException("User unable to authenticate");
        }

        // Return UserLoginResponseDTO
        return UserLoginDTO.builder()
                .userType(userType)
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
