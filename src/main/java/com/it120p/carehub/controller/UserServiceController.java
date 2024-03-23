package com.it120p.carehub.controller;

import com.it120p.carehub.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/service")
public class UserServiceController {

    @PostMapping("/")
    public void setUserService(
            Authentication authentication,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "location") String location,
            @RequestParam(name = "offerings") List<String> offerings
    ) {}

    @GetMapping("/")
    public UserService getUserService(
            Authentication authentication
    ) { return null; }

    @PutMapping("/")
    public void updateUserService(
            Authentication authentication,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "location") String location,
            @RequestParam(name = "offerings") List<String> offerings
    ) {}

    @DeleteMapping("/")
    public void deleteUserService(
            Authentication authentication
    ) {}
}
