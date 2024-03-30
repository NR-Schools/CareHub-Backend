package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User getUserInformation(Authentication authentication) throws MissingException {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new MissingException("User not found");
        }
        return user;
    }

    @PutMapping
    public User updateUserInformation(
            Authentication authentication,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "contactNo", required = false) String contactNo,
            @RequestPart(name = "profilePic", required = false) MultipartFile profilePic
    ) throws MissingException {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            throw new MissingException("User not found");
        }

        // Update user information
        if (name != null) {
            user.setName(name);
        }
        if (birthDate != null) {
            user.setBirthDate(birthDate);
        }
        if (email != null) {
            // Handle email update
            user.setEmail(email);
        }
        if (password != null) {
            user.setPassword(password);
        }
        if (contactNo != null) {
            user.setContactNo(contactNo);
        }
        if (profilePic != null) {
            // Save profile picture
        }

        userService.updateUser(user);
        return user;
    }
}
