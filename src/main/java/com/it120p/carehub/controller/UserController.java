package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.dto.UserInfoDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserInfoDTO getSelfUserInformation(
            Authentication authentication
    ) {
        User selfUser = ((User) authentication.getPrincipal());
        return UserInfoDTO.fromUser(selfUser);
    }

    @GetMapping("/email")
    public UserInfoDTO getOtherUserByEmail(
            @RequestParam("email") String email
    ) throws Exception {

        // Find user by email
        User otherUser = userService.getUserByEmail(email);

        if (otherUser == null) throw new MissingException("User");

        return UserInfoDTO.fromUser(otherUser);
    }

    @GetMapping("/name")
    public List<UserInfoDTO> getOtherUserByName(
            @RequestParam("name") String name
    ) {
        return userService.getUsersByName(name)
                .stream()
                .map(UserInfoDTO::fromUser)
                .toList();
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
