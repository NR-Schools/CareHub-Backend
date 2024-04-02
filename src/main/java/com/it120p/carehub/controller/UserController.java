package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.dto.UserInfoDTO;
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

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserInfoDTO getUserInformation(
            Authentication authentication
    ) throws Exception {

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email);

        if (user == null) throw new MissingException("User");

        return UserInfoDTO.fromUser(user);
    }

    @PutMapping
    public UserInfoDTO updateUserInformation(
            Authentication authentication,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestParam(name = "contactNo", required = false) String contactNo,
            @RequestPart(name = "profilePic", required = false) MultipartFile profilePic
    ) throws Exception {

        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) throw new MissingException("User");

        // Update user information
        if (name != null) user.setName(name);
        if (birthDate != null) user.setBirthDate(birthDate);
        if (contactNo != null) user.setContactNo(contactNo);
        if (profilePic != null) user.setPhotoBytes(profilePic.getBytes());

        User updatedUser = userService.updateUser(user);
        return UserInfoDTO.fromUser(updatedUser);
    }
}
