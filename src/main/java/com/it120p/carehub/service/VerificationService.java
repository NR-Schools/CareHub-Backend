package com.it120p.carehub.service;

import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.Verification;
import com.it120p.carehub.repository.VerificationRepository;

@Service
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private EmailService emailService;

    public static String generateRandomString(int length) {
        String characters = "0123456789";
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
        String code = generateRandomString(6);

        // Send email
        emailService.sendVerificationMail(user.getEmail(), code);

        // Add to verification table
        verificationRepository.save(
                Verification.builder()
                        .user(user)
                        .code(code)
                        .build()
        );
    }

    public boolean attemptVerifyCode(User user, String code) {
        // Check user from repository
        Verification verificationCode = verificationRepository.findVerificationCodeByUser(user).orElseThrow();
    
        // Check code
        boolean status =  verificationCode.getCode().equals(code);

        // Remove verification entry if successful
        if (status) verificationRepository.delete(verificationCode);

        // Return status
        return status;
    }
}
