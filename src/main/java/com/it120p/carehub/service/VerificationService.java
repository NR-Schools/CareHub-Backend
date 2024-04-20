package com.it120p.carehub.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.Verification;
import com.it120p.carehub.repository.VerificationCodeRepository;

@Service
public class VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public void sendVerificationCode(User user) {

        // Generate code
        String code = generateRandomString(5);

        // Send email
        emailService.sendVerificationMail(user.getEmail(), code);

        // Add to verification table
        verificationCodeRepository.save(
                Verification.builder()
                        .user(user)
                        .code(code)
                        .expiryDate(
                                LocalDateTime.now().plusMinutes(15)
                        )
                        .build()
        );
    }

    public boolean attemptVerifyCode(User user, String code) {
        // Check user from repository
        Verification verificationCode = verificationCodeRepository.findVerificationCodeByUser(user).orElseThrow();

        // Check date if not expired
        if (verificationCode.getExpiryDate().isAfter(LocalDateTime.now())) return false;
    
        // Check code
        return verificationCode.getCode().equals(code);
    }
}
