package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.AlreadyExistException;
import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserServiceCare;
import com.it120p.carehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/service")
public class UserServiceCareController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public void setUserService(
            Authentication authentication,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "location") String location,
            @RequestParam(name = "offerings") List<String> offerings
    ) throws Exception {

        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check if service is not yet set
        if (user.getUserServiceCare() != null) {
            throw new AlreadyExistException("User Service");
        }

        // Set Service
        UserServiceCare userServiceCare = UserServiceCare.builder()
                .type(type)
                .description(description)
                .offerings(offerings)
                .build();
        user.setUserServiceCare(userServiceCare);

        userService.updateUser(user);
    }

    @GetMapping("/")
    public UserServiceCare getUserService(
            Authentication authentication
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Return UserServiceCare
        return user.getUserServiceCare();
    }

    @PutMapping("/")
    public void updateUserService(
            Authentication authentication,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "location") String location,
            @RequestParam(name = "offerings") List<String> offerings
    ) throws Exception {

        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check if service is not yet set
        if (user.getUserServiceCare() == null) {
            throw new MissingException("User Service");
        }

        // Set Service
        UserServiceCare userServiceCare = UserServiceCare.builder()
                .type(type)
                .description(description)
                .offerings(offerings)
                .build();
        user.setUserServiceCare(userServiceCare);

        userService.updateUser(user);
    }

    @DeleteMapping("/")
    public void deleteUserService(
            Authentication authentication
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Remove UserServiceCare
        user.setUserServiceCare(null);
        userService.updateUser(user);
    }
}
