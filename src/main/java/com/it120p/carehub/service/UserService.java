package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUserExistByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public User updateUser(User user) {
        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
        if (optionalUser.isEmpty()) return null;

        user.setUserId(optionalUser.get().getUserId());
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.orElse(null);
    }

    public List<User> getUsersByName(String nameKeyword) {
        return userRepository.findUsersByName(nameKeyword);
    }
}
