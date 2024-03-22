package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.EmailAlreadyExistException;
import com.it120p.carehub.model.dto.UserRegisterDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.AuthService;
import com.it120p.carehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserRegisterDTO registerAccount(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "contactNo", required = false) String contactNo,
            @RequestParam(name = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate
    ) throws Exception {

        // Check if email is unique
        if (userService.isUserExistByEmail(email)) {
            throw new EmailAlreadyExistException();
        }

        // Initial Registration of email
        User newUser = authService.registerUser(email, password);

        // Update User
        newUser.setName(name);
        newUser.setContactNo(contactNo);
        newUser.setBirthDate(birthDate);
        User updateUser = userService.updateUser(newUser);

        // Return DTO
        return UserRegisterDTO.fromUser(updateUser);
    }
}
