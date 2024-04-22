package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.dto.UserInfoDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.ResourceService;
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

    @Autowired
    private ResourceService resourceService;


    @GetMapping
    public UserInfoDTO getSelfUserInformation(
            Authentication authentication
    ) {
        User selfUser = ((User) authentication.getPrincipal());
        return UserInfoDTO.fromUser(selfUser);
    }

    @GetMapping("/query")
    public List<UserInfoDTO> getOtherUserByEmail(
            @RequestParam("type") String type,
            @RequestParam("query") String query
    ) throws Exception {

        List<UserInfoDTO> results = new java.util.ArrayList<>(List.of());

        if (type.equals("email")) {
            User otherUser = userService.getUserByEmail(query);
            if (otherUser == null) throw new MissingException("User");
            results.add(
                    UserInfoDTO.fromUser(otherUser)
            );
        } else if (type.equals("name")) {
            results = userService.getUsersByName(query)
                    .stream()
                    .map(UserInfoDTO::fromUser)
                    .toList();
        }

        return results;
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
        if (profilePic != null) user.setPhotoResource( resourceService.setUserResource(profilePic) );

        User updatedUser = userService.updateUser(user);
        return UserInfoDTO.fromUser(updatedUser);
    }

}
